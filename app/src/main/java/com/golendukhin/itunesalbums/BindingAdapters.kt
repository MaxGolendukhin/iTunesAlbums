package com.golendukhin.itunesalbums

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.golendukhin.itunesalbums.data.Album
import com.golendukhin.itunesalbums.data.Song
import com.golendukhin.itunesalbums.details.SongsListAdapter
import com.golendukhin.itunesalbums.grid.AlbumsGridAdapter
import com.golendukhin.itunesalbums.grid.ApiStatus


@BindingAdapter("gridData")
fun bindAlbumsRecyclerView(recyclerView: RecyclerView, data: List<Album>?) {
    val adapter = recyclerView.adapter as AlbumsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData")
fun bindSongsRecyclerView(recyclerView: RecyclerView, data: List<Song>?) {
    val adapter = recyclerView.adapter as SongsListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imgUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, apiStatus: ApiStatus?) {
    when (apiStatus) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("albumYear")
fun TextView.textToYear(releaseDate: String) {
//    val simpleDateFormat = SimpleDateFormat(releaseDate)
//    text = simpleDateFormat.format(Date())

    text = releaseDate.subSequence(0, 10)
}
