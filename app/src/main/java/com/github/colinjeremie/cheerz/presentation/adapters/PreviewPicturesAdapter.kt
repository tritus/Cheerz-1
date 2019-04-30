package com.github.colinjeremie.cheerz.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.cheerz.presentation.adapters.viewholders.PreviewPicturesViewHolder
import com.github.colinjeremie.domain.Picture

class PreviewPicturesAdapter : RecyclerView.Adapter<PreviewPicturesViewHolder>() {

    var items: List<Picture> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewPicturesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_picture_item_view, parent, false)

        return PreviewPicturesViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PreviewPicturesViewHolder, position: Int) {
        holder.bindData(items[position])
    }
}