package com.github.colinjeremie.cheerz.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        private const val EXTRA_DATE = "extra_date"

        fun createIntent(context: Context, title: String, pictureUrl: String, description: String, date: Date) =
                Intent(context, DetailsActivity::class.java).apply {
                    putExtra(EXTRA_TITLE, title)
                    putExtra(EXTRA_DESCRIPTION, description)
                    putExtra(EXTRA_PICTURE_URL, pictureUrl)
                    putExtra(EXTRA_DATE, date)
                }
    }

    private val imageView: ImageView by lazy { findViewById<ImageView>(R.id.image_view) }
    private val dateTextView: TextView by lazy { findViewById<TextView>(R.id.date_text_view) }
    private val descriptionTextView: TextView by lazy { findViewById<TextView>(R.id.description_text_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val pictureUrl = intent.getStringExtra(EXTRA_PICTURE_URL)
        val date = intent.getSerializableExtra(EXTRA_DATE) as Date

        dateTextView.text = SimpleDateFormat.getDateInstance(MEDIUM, Locale.getDefault()).format(date)
        descriptionTextView.text = description
        Picasso.get().load(pictureUrl).into(imageView)
    }
}
