package com.sam.movielicious.ui.movies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import com.sam.movielicious.databinding.ActivityMovieListBinding
import com.sam.movielicious.model.AppDatabase
import com.sam.movielicious.utils.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_movie_list.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var viewModel: MoviesListViewModel
    private lateinit  var db : AppDatabase

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        db = Room.databaseBuilder(this, AppDatabase::class.java, "MovieDetailModel")
            .fallbackToDestructiveMigration()
            .build()

            binding = DataBindingUtil.setContentView(this, com.sam.movielicious.R.layout.activity_movie_list)
                val metrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(metrics)

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.moviesList.setLayoutManager(GridLayoutManager(this, 2))
        } else {
            binding.moviesList.setLayoutManager(GridLayoutManager(this, 4))
        }

            viewModel = ViewModelProviders.of(this).get(MoviesListViewModel::class.java)
            viewModel.errorMessage.observe(this, Observer { errorMessage ->
                if (errorMessage != null)

                    showError(errorMessage)
                else
                    hideError()
            })
            binding.viewModel = viewModel

            addpagination()

    }

    private fun addpagination() {
        binding.moviesList.addOnScrollListener(object :
            PaginationScrollListener(binding.moviesList.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                //you have to call loadmore items to get more data
                if (!isLastPage)
                    getMoreItems()

            }
        })

        swiperefresh.setOnRefreshListener( {

            swiperefresh.setRefreshing(false)
            viewModel.page=1
            viewModel.clearList()

        })
    }

    private fun showEndPart(msg: String) {
        errorSnackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
        errorSnackbar?.show()
    }

    private fun showError(@StringRes errorMessage: Int) {

        if (!isLastPage) {
            errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar?.setAction(com.sam.movielicious.R.string.retry, viewModel.errorClickListener)
        } else {
            errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
        }
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    fun getMoreItems() {
        if (viewModel.total_pages > viewModel.page) {
            viewModel.page++

            viewModel.loadMovies()


        } else {
            isLastPage = true
            if (errorSnackbar == null || errorSnackbar != null && !errorSnackbar!!.isShown) {
                showError(com.sam.movielicious.R.string.no_more_results)
            }
        }

    }
}
