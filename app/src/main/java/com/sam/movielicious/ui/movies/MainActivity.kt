package com.sam.movielicious.ui.movies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import com.sam.movielicious.R
import com.sam.movielicious.databinding.ActivityMovieListBinding
import com.sam.movielicious.utils.PaginationScrollListener


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var viewModel: MoviesListViewModel


    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
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
    }

    private fun showEndPart(msg: String) {
        errorSnackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
        errorSnackbar?.show()
    }

    private fun showError(@StringRes errorMessage: Int) {

        if (!isLastPage) {
            errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
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
        if (5 > viewModel.page) {
            viewModel.page++
            viewModel.loadMovies()
        } else {
            isLastPage = true
            if (errorSnackbar == null || errorSnackbar != null && !errorSnackbar!!.isShown) {
                showError(R.string.no_more_results)
            }
        }

    }
}
