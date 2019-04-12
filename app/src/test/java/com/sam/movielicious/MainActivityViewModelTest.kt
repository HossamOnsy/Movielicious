//package com.sam.movielicious
//
//import android.arch.core.executor.testing.InstantTaskExecutorRule
//import com.sam.movielicious.ui.movies.MoviesListViewModel
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//import java.net.HttpURLConnection
//
//
//class MainActivityViewModelTest {
//
//
//
//
//    private lateinit var moviesListViewModel: MoviesListViewModel
//
//
//    @Rule
//    @JvmField
//    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously
//
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//    }
//
//    @Test
//    fun showDataFromApi() {
////        val movieResponse = MoviesListModel(1,ArrayList<MovieListItemModel>(),1,1)
////        moviesListViewModel = MoviesListViewModel()
////        Mockito.`when`(moviesListViewModel.loadMovies()).then {
////            Mockito.verify(moviesListViewModel.errorMessage.value==null);
////        }
//
//        this.mockHttpResponse("getUser_whenSuccess.json", HttpURLConnection.HTTP_OK)
//        // Pre-test
//        assertEquals(null, this.viewModel.user.value, "User should be null because stream not started yet")
//        // Execute View Model
//        this.viewModel.getUser()
//        // Checks
//        assertEquals(EXPECTED_USER, this.viewModel.user.value, "User must be fetched")
//        assertEquals(false, this.viewModel.isLoading.value, "Should be reset to 'false' because stream ended")
//        assertEquals(null, this.viewModel.errorMessage.value, "No error must be founded")
//
//        // Validation
//
//
//    }
//
//}