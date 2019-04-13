package com.sam.movielicious

import com.sam.movielicious.model.MovieListItemModel
import com.sam.movielicious.model.MoviesListModel
import com.sam.movielicious.network.RestApi
import com.sam.movielicious.ui.movies.MoviesListViewModel
import com.sam.movielicious.utils.API_KEY
import io.reactivex.Observable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MovieListAPITest {

    @Mock
    var restApi: RestApi? = null

    @Mock
    var moviesListViewModel: MoviesListViewModel? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadingMoviesUsingApi() {
        //Given
//        `when`(restApi?.getTopRatedMovies( API_KEY,1)).thenReturn(Observable.just(MoviesListModel(1,githubUserList(),2,2)))

        moviesListViewModel?.loadMovies()

        //When
        val subscriber = TestSubscriber<List<MoviesListModel>>()

        subscriber.assertNoErrors()

        verify(moviesListViewModel, times(1))?.loadMovies();

    }


    private fun githubUserList(): List<MovieListItemModel> {

        val movieListItemModels = ArrayList<MovieListItemModel>()

        var movieListItem = MovieListItemModel(false,"", emptyList(),1733,"","",""
        ,2.0,"","","",false,1.0,1)

        movieListItemModels.add(movieListItem)
        return movieListItemModels
    }




}