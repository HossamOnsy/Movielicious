package com.sam.movielicious.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface MovieDao {
    @Query("DELETE  FROM MovieDetailModel Where id = :id ")
    fun deleteModel(id: Int)


    @Query("SELECT * FROM MovieDetailModel Where id = :id ")
    fun getModel(id: Int): MovieDetailModel

    @get:Query("SELECT * FROM MovieDetailModel ")
    val loadAllModels: List<MovieDetailModel>

    @Insert
    fun insert(vararg movieDetailModel: MovieDetailModel)
}