package com.github.colinjeremie.cheerz.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.cheerz.framework.TestPictureSource
import com.github.colinjeremie.cheerz.presentation.adapters.PreviewPicturesAdapter
import com.github.colinjeremie.data.PicturesRepository
import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity(), MainPresenter.Interaction {

    private val gson: Gson by lazy {
        GsonBuilder().setDateFormat("YYYY-MM-DD").create()
    }

    private val repository: PicturesRepository by lazy {
        PicturesRepository(TestPictureSource(gson))
    }
    private val presenter: MainPresenter by lazy {
        MainPresenter(this, GetPicturesUseCase(repository))
    }

    private val button: Button by lazy { findViewById<Button>(R.id.retrieve_button) }
    private val loadingView: View by lazy { findViewById<View>(R.id.loading_view) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val adapter: PreviewPicturesAdapter by lazy { PreviewPicturesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            presenter.onRetrieveButtonClicked()
        }
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    override fun onRefresh() {
        button.visibility = View.GONE
        recyclerView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    override fun onRefreshDone() {
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun render(pictures: List<Picture>) {
        adapter.items = pictures
    }
}
