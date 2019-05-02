package com.github.colinjeremie.cheerz.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.cheerz.presentation.fullscreen.FullScreenPictureDialogFragment
import org.koin.androidx.scope.currentScope
import java.util.*

class DetailsActivity : AppCompatActivity(), DetailsPresenter.Interaction {

    companion object {

        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_PICTURE_URL = "extra_picture_url"
        const val EXTRA_PICTURE_HD_URL = "extra_picture_hd_url"
        const val EXTRA_DATE = "extra_date"

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
    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val imageView: ImageView by lazy { findViewById<ImageView>(R.id.header_view) }
    private val dateTextView: TextView by lazy { findViewById<TextView>(R.id.date_text_view) }
    private val descriptionTextView: TextView by lazy { findViewById<TextView>(R.id.description_text_view) }

    private val presenter: DetailsPresenter by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        presenter.subscribe(this)
        presenter.load(intent)
    }

    override fun displayDate(date: String) {
        dateTextView.text = date
    }

    override fun displayTitle(title: String) {
        this.titleTextView.text = title
    }

    override fun displayDescription(description: String) {
        descriptionTextView.text = description
    }

    override fun getTargetImageView(): ImageView = imageView

    override fun displayPictureInHd(url: String) {
        FullScreenPictureDialogFragment.show(
            url,
            supportFragmentManager
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        presenter.onCreateOptionsMenu(menuInflater, menu, supportActionBar)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            presenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }
}
