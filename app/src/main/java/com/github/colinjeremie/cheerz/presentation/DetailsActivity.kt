package com.github.colinjeremie.cheerz.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.colinjeremie.cheerz.R
import com.squareup.picasso.Picasso
import java.text.DateFormat.MEDIUM
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_DESCRIPTION = "extra_description"
        private const val EXTRA_PICTURE_URL = "extra_picture_url"
        private const val EXTRA_PICTURE_HD_URL = "extra_picture_hd_url"
        private const val EXTRA_DATE = "extra_date"

        fun createIntent(context: Context, title: String, pictureUrl: String, pictureHdUrl: String?, description: String, date: Date) =
                Intent(context, DetailsActivity::class.java).apply {
                    putExtra(EXTRA_TITLE, title)
                    putExtra(EXTRA_DESCRIPTION, description)
                    putExtra(EXTRA_PICTURE_URL, pictureUrl)
                    putExtra(EXTRA_PICTURE_HD_URL, pictureHdUrl)
                    putExtra(EXTRA_DATE, date)
                }
    }

    private val titleTextView: TextView by lazy { findViewById<TextView>(R.id.title_text_view) }
    private val imageView: ImageView by lazy { findViewById<ImageView>(R.id.image_view) }
    private val dateTextView: TextView by lazy { findViewById<TextView>(R.id.date_text_view) }
    private val descriptionTextView: TextView by lazy { findViewById<TextView>(R.id.description_text_view) }

    private var pictureHdUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        pictureHdUrl = intent.getStringExtra(EXTRA_PICTURE_HD_URL)

        val pictureUrl = intent.getStringExtra(EXTRA_PICTURE_URL)
        val date = intent.getSerializableExtra(EXTRA_DATE) as Date

        dateTextView.text = SimpleDateFormat.getDateInstance(MEDIUM, Locale.getDefault()).format(date)
        titleTextView.text = intent.getStringExtra(EXTRA_TITLE)
        descriptionTextView.text = intent.getStringExtra(EXTRA_DESCRIPTION)

        Picasso.get().load(pictureUrl).into(imageView)
    }

    private fun displayFullscreen() {
        pictureHdUrl?.let {
            FullScreenPictureDialogFragment.show(it, supportFragmentManager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (pictureHdUrl != null) {
            menuInflater.inflate(R.menu.details_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                R.id.action_fullscreen -> {
                    displayFullscreen()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
