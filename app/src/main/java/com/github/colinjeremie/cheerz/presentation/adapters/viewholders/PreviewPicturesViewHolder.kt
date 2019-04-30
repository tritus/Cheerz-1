package com.github.colinjeremie.cheerz.presentation.adapters.viewholders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.domain.Picture
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PreviewPicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.preview_picture_image_view) }
    private val retryButton: Button by lazy { itemView.findViewById<Button>(R.id.retry_button) }
    private val loadingView: View by lazy { itemView.findViewById<View>(R.id.loading_view) }

    fun bindData(data: Picture) {
        retryButton.setOnClickListener {
            loadPicture(data.url)
        }
        loadPicture(data.url)
    }

    private fun loadPicture(url: String) {
        retryButton.visibility = View.GONE
        loadingView.visibility = View.VISIBLE

        Picasso.get().load(url).into(imageView, object : Callback {
            override fun onSuccess() {
                loadingView.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                loadingView.visibility = View.GONE
                retryButton.visibility = View.VISIBLE
            }

        })
    }
}