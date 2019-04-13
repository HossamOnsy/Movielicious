package com.sam.movielicious.ui.movies

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import com.sam.movielicious.base.BaseViewModel
import com.sam.movielicious.R
import com.sam.movielicious.model.MoviesListModel
import com.sam.movielicious.network.RestApi
import com.sam.movielicious.utils.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesListViewModel : BaseViewModel() {
    @Inject
    lateinit var restApi: RestApi

    var page = 1
    var total_pages = 2

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val movieListAdapter: MovieListAdapter =
        MovieListAdapter()

    val errorClickListener = View.OnClickListener { loadMovies() }

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    init {
        loadMovies()
    }

    fun loadMovies() {

        subscription = restApi.getTopRatedMovies(API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMoviesListStart() }
            .doOnTerminate { onRetrieveMoviesListFinish() }
            .subscribe(
                { result -> onRetrieveMoviesListSuccess(result) },
                { error -> onRetrieveMoviesListError(error) }
            )
    }

    fun clearList(){
        movieListAdapter.clearList()
        loadMovies()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveMoviesListStart() {
        loadingVisibility.value = View.VISIBLE

        errorMessage.value = null
    }

    private fun onRetrieveMoviesListFinish() {

        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMoviesListSuccess(moviesListModel: MoviesListModel) {
        total_pages = moviesListModel.total_pages
        if (total_pages > page) {
            movieListAdapter.updateMovieList(moviesListModel.results)

        }

    }

    private fun onRetrieveMoviesListError(error: Throwable) {
        Log.v("error","error -> " + error.message)
        errorMessage.value = R.string.post_error
    }
}