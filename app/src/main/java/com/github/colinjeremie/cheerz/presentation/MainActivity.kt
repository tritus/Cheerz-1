package com.github.colinjeremie.cheerz.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.cheerz.framework.InMemoryStoragePictureSource
import com.github.colinjeremie.cheerz.framework.NetworkPictureSource
import com.github.colinjeremie.cheerz.presentation.adapters.PreviewPicturesAdapter
import com.github.colinjeremie.data.PicturesRepository
import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity(), MainPresenter.Interaction {

    private val gson: Gson by lazy {
        GsonBuilder().setDateFormat("yyyy-MM-dd").create()
    }

    private val repository: PicturesRepository by lazy {
        PicturesRepository(NetworkPictureSource(gson), InMemoryStoragePictureSource())
    }
    private val presenter: MainPresenter by lazy {
        MainPresenter(this, GetPicturesUseCase(repository))
    }

    private val retrieveButton: Button by lazy { findViewById<Button>(R.id.retrieve_button) }
    private val retrieveNewPicturesButton: Button by lazy { findViewById<Button>(R.id.retrieve_new_pictures_button) }
    private val loadingView: View by lazy { findViewById<View>(R.id.loading_view) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val numberEditText: EditText by lazy { findViewById<EditText>(R.id.last_number_of_pictures_view) }
    private val titleView: View by lazy { findViewById<View>(R.id.last_number_of_pictures_title_view) }

    private val adapter: PreviewPicturesAdapter by lazy { PreviewPicturesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrieveButton.setOnClickListener {
            presenter.onRetrieveButtonClicked()
        }
        retrieveNewPicturesButton.setOnClickListener {
            presenter.onRetrieveNewPicturesButtonClicked()
        }
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    override fun resetView() {
        titleView.visibility = View.VISIBLE
        numberEditText.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.GONE
        retrieveNewPicturesButton.visibility = View.GONE
        retrieveButton.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        titleView.visibility = View.GONE
        numberEditText.visibility = View.GONE
        retrieveButton.visibility = View.GONE
        recyclerView.visibility = View.GONE
        retrieveNewPicturesButton.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    override fun onRefreshSuccess() {
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        retrieveNewPicturesButton.visibility = View.VISIBLE
    }

    override fun onRefreshFailure() {
        resetView()
    }

    override fun getNumberOfPicturesToRetrieve(): Int = numberEditText.text.toString().toInt()

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
}
