package com.sam.movielicious

import android.content.Context
import com.sam.movielicious.network.RestApi
import com.sam.movielicious.ui.moviedetails.MovieDetailsVM
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MovieDetailsAPITest {

    @Mock
    var restApi: RestApi? = null

    @Mock
    var movieDetailsVM: MovieDetailsVM? = null
    @Mock
    var context = mock(Context::class.java)
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadingMovieDetailsUsingApi() {

        movieDetailsVM?.loadMovieDetails(context, true)
        verify(movieDetailsVM, times(1))?.loadMovieDetails(context, true);

    }

}