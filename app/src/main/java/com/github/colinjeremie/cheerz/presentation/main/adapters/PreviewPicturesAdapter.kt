package com.github.colinjeremie.cheerz.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.cheerz.framework.models.Picture
import com.github.colinjeremie.cheerz.presentation.main.adapters.viewholders.PreviewPicturesViewHolder

class PreviewPicturesAdapter(private val interaction: Interaction?) : RecyclerView.Adapter<PreviewPicturesViewHolder>() {

    var items: List<Picture> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewPicturesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_picture_item_view, parent, false)

        return PreviewPicturesViewHolder(view, interaction)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PreviewPicturesViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    interface Interaction {
        fun onItemClicked(picture: Picture)
        fun onItemLongClicked(picture: Picture)
    }
}