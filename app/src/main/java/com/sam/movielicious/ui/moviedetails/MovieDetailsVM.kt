package com.sam.movielicious.ui.moviedetails

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import android.view.View
import com.sam.movielicious.BaseViewModel
import com.sam.movielicious.R
import com.sam.movielicious.model.AppDatabase
import com.sam.movielicious.model.MovieDetailModel
import com.sam.movielicious.network.RestApi
import com.sam.movielicious.utils.API_KEY
import com.sam.movielicious.utils.POSTERPATHORIGINAL
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieDetailsVM() : BaseViewModel() {
    @Inject
    lateinit var restApi: RestApi

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {  }

    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    var id = ""
    var movieDetailsModel= MovieDetailModel()
    lateinit var db :AppDatabase


    fun loadMovieDetails(applicationContext: Context,checkInDatabase:Boolean) {

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "MovieDetailModel")
            .fallbackToDestructiveMigration()
            .build()

        if(checkInDatabase)
            subscription =
                Observable.fromCallable {
                    db.movieDao().getModel(id.toInt()) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {onRetrieveMovieDetailsStart() }
                    .doOnTerminate { onRetrieveMovieDetailsFinish()}
                    .subscribe(
                        { result ->
                            movieDetailsModel = result
                            movieDetailsModel.favorite=true
                            movieFav.value=true
                            onRetrieveMovieDetailsSuccess(result) },
                        { error ->
                            movieDetailsModel.favorite=false
                            movieFav.value=false
                            loadMovieDetails(applicationContext,false)

                        }
                    )
        else{
            subscription = restApi.getMovieDetails(id,API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {onRetrieveMovieDetailsStart() }
                    .doOnTerminate { onRetrieveMovieDetailsFinish()}
                    .subscribe(
                        { result ->
                            onRetrieveMovieDetailsSuccess(result) },
                        { error -> onRetrieveMovieDetailsError(error)

                        }
                    )
        }

    }

    fun insertModel(applicationContext: Context) {
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "MovieDetailModel")
            .fallbackToDestructiveMigration()
            .build()
        if(movieFav.value==false)
           subscription = Observable.fromCallable { db.movieDao().insert(movieDetailsModel) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doOnTerminate { }
                .subscribe(
                    { result -> movieDetailsModel.favorite=true
                        movieFav.value=true
                        db.close()},
                    { error -> movieDetailsModel.favorite=true
                        movieFav.value=true
                        db.close()
                        Log.v("error", "error -> " + error.message)}
                )
        else
            subscription = Observable.fromCallable { db.movieDao().deleteModel(id.toInt()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doOnTerminate {  }
                .subscribe(
                    { result -> movieDetailsModel.favorite=false
                        movieFav.value=false },
                    { error -> movieDetailsModel.favorite=true
                        movieFav.value=true
                        Log.v("error", "error -> " + error.message)}
                )


    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveMovieDetailsStart() {
        loadingVisibility.value = View.VISIBLE

        errorMessage.value = null
    }

    private fun onRetrieveMovieDetailsFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieDetailsSuccess(movieDetailsModel: MovieDetailModel) {

        bind(movieDetailsModel)
        this.movieDetailsModel = movieDetailsModel

    }

    fun onRetrieveMovieDetailsError(error: Throwable) {
        Log.v("error", "error -> " + error.message)
        errorMessage.value = R.string.post_error
    }

    private val movieTitle = MutableLiveData<String>()
    private val movieOverview = MutableLiveData<String>()
    private val movieReleaseDate = MutableLiveData<String>()
    val movieURL = MutableLiveData<String>()
    val moviewId = MutableLiveData<String>()
    val movieVote = MutableLiveData<String>()
    val movieFav = MutableLiveData<Boolean>()


    fun bind(movieDetailsModel: MovieDetailModel) {

        movieTitle.value = movieDetailsModel.title
        movieOverview.value = movieDetailsModel.overview
        movieURL.value = POSTERPATHORIGINAL + movieDetailsModel.backdrop_path
        moviewId.value = movieDetailsModel.id.toString()
        movieVote.value = movieDetailsModel.vote_average.toString()
        movieReleaseDate.value = movieDetailsModel.release_date
        movieFav.value = movieDetailsModel.favorite

    }


    fun getMovieTitle(): MutableLiveData<String> {
        if (movieTitle.value != null)
            return movieTitle
        else {
            movieTitle.value = ""
            return movieTitle
        }
    }

    fun getMovieOverview(): MutableLiveData<String> {
        if (movieOverview.value != null)
            return movieOverview
        else {
            movieOverview.value = ""
            return movieOverview
        }
    }


    fun getMovieId(): MutableLiveData<String> {
        if (moviewId.value != null)
            return moviewId
        else {
            moviewId.value = ""
            return moviewId
        }
    }

    fun getMovieReleaseDate(): MutableLiveData<String> {
        if (movieReleaseDate.value != null)
            return movieReleaseDate
        else {
            movieReleaseDate.value = ""
            return movieReleaseDate
        }
    }

}