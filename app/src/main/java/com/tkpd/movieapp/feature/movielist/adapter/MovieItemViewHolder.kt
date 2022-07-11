package com.tkpd.movieapp.feature.movielist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tkpd.movieapp.databinding.LayoutMovieItemBinding
import com.tkpd.movieapp.model.MovieItem
import com.tkpd.movieapp.util.loadImage

class MovieItemViewHolder(
    private val itemViewBinding: LayoutMovieItemBinding,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemViewBinding.root) {

    interface Listener{
        fun onMovieItemClicked(movieItem: MovieItem)
    }

    fun bind(movieItem: MovieItem) {
        itemViewBinding.apply {
            root.setOnClickListener {
                listener.onMovieItemClicked(movieItem)
            }
            textTitle.text = movieItem.title
            ivMovieCover.loadImage(movieItem.posterPath)
        }
    }

}