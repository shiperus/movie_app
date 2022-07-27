package com.tkpd.movieapp.feature.movielist.adapter

import android.graphics.*
import androidx.core.graphics.drawable.toBitmap
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

            ivMovieCover.viewTreeObserver.addOnDrawListener {
                val bitmap = ivMovieCover.getDrawable().toBitmap()
                val roundBitmap = getRoundedCornerBitmap(bitmap, 60)
                ivMovieCover.setImageBitmap(roundBitmap)
            }
        }
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
}