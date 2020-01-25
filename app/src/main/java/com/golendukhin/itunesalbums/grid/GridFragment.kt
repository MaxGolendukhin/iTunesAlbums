package com.golendukhin.itunesalbums.grid

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.golendukhin.itunesalbums.R
import com.golendukhin.itunesalbums.databinding.GridFragmentBinding

class GridFragment : Fragment() {
    private val gridLayoutModel: GridLayoutModel by lazy {
        ViewModelProviders.of(this).get(GridLayoutModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = GridFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = gridLayoutModel

        binding.albumsGrid.adapter = AlbumsGridAdapter(AlbumsGridAdapter.OnClickListener {
            gridLayoutModel.displayAlbumDetails(it)
        })
        gridLayoutModel.navigateToSelectedAlbum.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(GridFragmentDirections.actionShowDetails(it))
                gridLayoutModel.displayAlbumDetailsCompleted()
            }
        })
        gridLayoutModel.searchTextMutableLiveData.observe(this, Observer(gridLayoutModel::getAlbums))

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.grid_menu, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchedAlbum: String?): Boolean {
                gridLayoutModel.searchTextMutableLiveData.postValue(searchedAlbum)
                Utils.hideSoftKeyBoard(context!!, searchView)
                return true
            }
            override fun onQueryTextChange(search: String?): Boolean { return false }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    object Utils {
        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}