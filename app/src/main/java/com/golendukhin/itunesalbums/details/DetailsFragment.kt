package com.golendukhin.itunesalbums.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.golendukhin.itunesalbums.R
import com.golendukhin.itunesalbums.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val album = DetailsFragmentArgs.fromBundle(arguments!!).album
        val detailViewModelFactory = DetailsViewModelFactory(album, application)
        val detailsFragmentViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailsFragmentViewModel::class.java)

        binding.viewModel = detailsFragmentViewModel
        binding.songsList.adapter = SongsListAdapter()

        return binding.root
    }
}