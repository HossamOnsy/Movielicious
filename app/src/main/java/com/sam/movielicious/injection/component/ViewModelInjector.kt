package com.sam.movielicious.injection.component

import com.sam.movielicious.injection.module.NetworkModule
import com.sam.movielicious.ui.moviedetails.MovieDetailsVM
import com.sam.movielicious.ui.movies.MoviesListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified MoviesListViewModel.
     * @param moviesListViewModel MoviesListViewModel in which to inject the dependencies
     */
    fun injectMovieList(moviesListViewModel: MoviesListViewModel)

    /**
     * Injects required dependencies into the specified MoviesListViewModel.
     * @param movieDetailsModel MoviesListViewModel in which to inject the dependencies
     */
    fun injectMovieDetails(movieDetailsVM: MovieDetailsVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}