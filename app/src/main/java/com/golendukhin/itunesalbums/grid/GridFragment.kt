package com.golendukhin.itunesalbums.grid

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.golendukhin.itunesalbums.databinding.GridActivityBinding


class GridFragment : Fragment() {
    private val gridLayoutModel: GridLayoutModel by lazy {
        ViewModelProviders.of(this).get(GridLayoutModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = GridActivityBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = gridLayoutModel

        binding.albumsGrid.adapter = AlbumsGridAdapter(AlbumsGridAdapter.OnClickListener {
            gridLayoutModel.displayAlbumDetails(it)
        })

        return binding.root
    }
}