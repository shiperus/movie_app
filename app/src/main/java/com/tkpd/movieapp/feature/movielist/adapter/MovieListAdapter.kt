package com.tkpd.movieapp.feature.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tkpd.movieapp.databinding.LayoutMovieItemBinding
import com.tkpd.movieapp.model.MovieItem

class MovieListAdapter(
    private val movieItemViewHolderListener: MovieItemViewHolder.Listener
) : RecyclerView.Adapter<MovieItemViewHolder>() {

    private var listMovie: List<MovieItem> = listOf()

    fun populateListMovie(listMovie: List<MovieItem>) {
        this.listMovie = listMovie
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val itemBinding = LayoutMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieItemViewHolder(itemBinding, movieItemViewHolderListener)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

}