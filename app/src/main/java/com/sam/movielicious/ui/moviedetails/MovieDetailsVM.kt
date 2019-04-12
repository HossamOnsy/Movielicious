package com.sam.movielicious.ui.moviedetails

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import com.sam.movielicious.BaseViewModel
import com.sam.movielicious.R
import com.sam.movielicious.model.MovieDetailModel
import com.sam.movielicious.network.RestApi
import com.sam.movielicious.utils.API_KEY
import com.sam.movielicious.utils.POSTERPATHORIGINAL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieDetailsVM(): BaseViewModel(){
    @Inject
    lateinit var restApi: RestApi

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadMovieDetails() }
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    var id=""





    fun loadMovieDetails(){
        subscription = restApi.getMovieDetails(id,API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMovieDetailsStart() }
            .doOnTerminate { onRetrieveMovieDetailsFinish() }
            .subscribe(
                { result -> onRetrieveMovieDetailsSuccess(result)},
                { error ->onRetrieveMovieDetailsError(error) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveMovieDetailsStart(){
        loadingVisibility.value = View.VISIBLE

        errorMessage.value = null
    }

    private fun onRetrieveMovieDetailsFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieDetailsSuccess(movieDetailsModel:MovieDetailModel){

        bind(movieDetailsModel)


    }

    fun onRetrieveMovieDetailsError(error: Throwable) {
        Log.v("error","error -> " + error.message)
        errorMessage.value = R.string.post_error
    }

    private val movieTitle = MutableLiveData<String>()
    private val movieOverview = MutableLiveData<String>()
    val movieURL = MutableLiveData<String>()
    val moviewId = MutableLiveData<String>()


    fun bind(movieDetailsModel: MovieDetailModel){
        movieTitle.value = movieDetailsModel.title
        movieOverview.value = movieDetailsModel.overview
        movieURL.value = POSTERPATHORIGINAL +movieDetailsModel.backdrop_path
        moviewId.value = movieDetailsModel.id.toString()
    }



    fun getMovieTitle():MutableLiveData<String>{
        if(movieTitle.value!=null)
         return movieTitle
        else {
            movieTitle.value = ""
            return movieTitle
        }
    }

    fun getMovieOverview():MutableLiveData<String>{
        if(movieOverview.value!=null)
            return movieOverview
        else {
            movieOverview.value = ""
            return movieOverview
        }
    }


    fun getMovieId():MutableLiveData<String>{
        if(moviewId.value!=null)
            return moviewId
        else {
            moviewId.value = ""
            return moviewId
        }
    }


}