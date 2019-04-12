package com.sam.movielicious.ui.movies

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sam.movielicious.BaseViewModel
import com.sam.movielicious.model.MovieListItemModel
import com.sam.movielicious.utils.POSTERPATHW185

class MovieItemViewModel(): BaseViewModel() {

    private val movieTitle = MutableLiveData<String>()
    private val movieOverview = MutableLiveData<String>()
    private val movieURL = MutableLiveData<String>()
    private val moviewId = MutableLiveData<String>()
    private val movieVote = MutableLiveData<String>()
    private val click = MutableLiveData<String>()


    fun bind(movieListItem: MovieListItemModel){
        movieTitle.value = movieListItem.title
        movieOverview.value = movieListItem.overview
        movieURL.value = POSTERPATHW185 +movieListItem.poster_path
        moviewId.value = movieListItem.id.toString()
        movieVote.value = movieListItem.vote_average.toString()+"/10.0"
    }

    fun getMovieTitle():MutableLiveData<String>{
        return movieTitle
    }

    fun getMovieOverview():MutableLiveData<String>{
        return movieOverview
    }

    fun getMoviePoster():MutableLiveData<String>{
        return movieURL
    }
    fun getMovieVote():MutableLiveData<String>{
        return movieVote
    }
    fun getMovieId():MutableLiveData<String>{
        Log.v("Heelo","hii")
        return moviewId
    }
}