package com.github.colinjeremie.cheerz.presentation.details

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import com.github.colinjeremie.cheerz.R
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailsPresenter {

    private var pictureHdUrl: String? = null
    var interaction: Interaction? = null

    fun load(intent: Intent) {
        pictureHdUrl = intent.getStringExtra(DetailsActivity.EXTRA_PICTURE_HD_URL)

        val pictureUrl = intent.getStringExtra(DetailsActivity.EXTRA_PICTURE_URL)
        val date = intent.getSerializableExtra(DetailsActivity.EXTRA_DATE) as Date

        interaction?.displayDate(SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date))
        interaction?.displayTitle(intent.getStringExtra(DetailsActivity.EXTRA_TITLE))
        interaction?.displayDescription(intent.getStringExtra(DetailsActivity.EXTRA_DESCRIPTION))

        Picasso.get().load(pictureUrl).into(interaction?.getTargetImageView())
    }

    fun onCreateOptionsMenu(menuInflater: MenuInflater, menu: Menu?, supportActionBar: ActionBar?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (pictureHdUrl != null) {
            menuInflater.inflate(R.menu.details_menu, menu)
        }
    }

    fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    interaction?.onBackPressed()
                    true
                }
                R.id.action_fullscreen -> {
                    pictureHdUrl?.let { interaction?.displayPictureInHd(it) }
                    true
                }
                else -> false
            }

    fun onDestroy() {
        interaction = null
    }

    interface Interaction {
        fun displayDate(date: String)
        fun displayTitle(title: String)
        fun displayDescription(description: String)
        fun getTargetImageView(): ImageView
        fun onBackPressed()
        fun displayPictureInHd(url: String)
    }
}
