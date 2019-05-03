package com.github.colinjeremie.cheerz.presentation.main

import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.cheerz.framework.models.Picture
import com.github.colinjeremie.cheerz.presentation.details.DetailsActivity
import com.github.colinjeremie.cheerz.presentation.fullscreen.FullScreenPictureDialogFragment
import com.github.colinjeremie.cheerz.presentation.main.adapters.PreviewPicturesAdapter
import org.koin.androidx.scope.currentScope

class MainActivity : AppCompatActivity(), MainPresenter.Interaction, PreviewPicturesAdapter.Interaction {

    private val loadingView: View by lazy { findViewById<View>(R.id.loading_view) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val presenter: MainPresenter by currentScope.inject()

    private val adapter: PreviewPicturesAdapter by lazy {
        PreviewPicturesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        title = ""
        presenter.subscribe(this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        recyclerView.adapter = adapter
        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        presenter.onSaveInstanceState(outState, adapter.items)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        presenter.onCreateOptionsMenu(menuInflater, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            presenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun resetView() {
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    override fun onRefresh() {
        recyclerView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    override fun onRefreshSuccess() {
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onRefreshFailure() {
        resetView()
    }

    override fun render(pictures: List<Picture>) {
        adapter.items = pictures
    }

    override fun showErrorMessage(@StringRes messageRes: Int) {
        val message =
                if (messageRes != -1) {
                    getString(messageRes)
                } else {
                    getString(R.string.error_message_generic)
                }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun displayFullScreenPicture(pictureHdUrl: String) {
        FullScreenPictureDialogFragment.show(
                pictureHdUrl,
                supportFragmentManager
        )
    }

    override fun onItemClicked(picture: Picture) {
        startActivity(
                DetailsActivity.createIntent(
                        this,
                        picture.title,
                        picture.url,
                        picture.hdUrl,
                        picture.explanation,
                        picture.date
                )
        )
    }

    override fun onItemLongClicked(picture: Picture) {
        presenter.onPictureLongClicked(picture)
    }

    override fun showRetrieveLastPicturesDialog() {
        val view = EditText(this).apply {
            hint = getString(R.string.alert_dialog_retrieve_last_pictures_hint)
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = InputType.TYPE_CLASS_NUMBER
        }

        AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.alert_dialog_retrieve_last_pictures_title)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    presenter.onRetrieveLastPicturesDialogButtonClicked(view.text.toString())
                }
                .show()
    }
}
