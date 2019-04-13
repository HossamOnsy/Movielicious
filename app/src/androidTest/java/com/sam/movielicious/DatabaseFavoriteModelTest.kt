package com.sam.movielicious

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sam.movielicious.model.AppDatabase
import com.sam.movielicious.model.MovieDetailModel
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class DatabaseFavoriteModelTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertFavoriteModelData() {
        val movieDetailModel = MovieDetailModel()
        movieDetailModel.id = 1234321
        movieDetailModel.favorite = true

        val datamodel = database.movieDao().getModel(movieDetailModel.id, movieDetailModel.favorite)
        assertEquals(datamodel.id, 1234321)
    }

    @Test
    fun deleteFavorite() {
        val movieDetailModel = MovieDetailModel()
        movieDetailModel.id = 1234321
        database.movieDao().deleteModel(movieDetailModel.id,true)

        val bufferoos = database.movieDao().getModel(movieDetailModel.id, movieDetailModel.favorite)
        assertEquals(bufferoos, null)
    }


}