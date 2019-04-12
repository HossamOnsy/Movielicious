package com.sam.movielicious

import android.arch.lifecycle.ViewModel
import com.sam.movielicious.di.component.DaggerViewModelInjector
import com.sam.movielicious.di.component.ViewModelInjector
import com.sam.movielicious.di.module.NetworkModule
import com.sam.movielicious.ui.moviedetails.MovieDetailsVM
import com.sam.movielicious.ui.movies.MoviesListViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MoviesListViewModel -> injector.injectMovieList(this)
            is MovieDetailsVM -> injector.injectMovieDetails(this)
        }
    }
}