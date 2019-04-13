package com.sam.movielicious.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieDetailModel Where id = :id ")
    fun getModel(id: Int): MovieDetailModel

    @Query("SELECT * FROM movieDetailModel ")
    fun loadAllModels(): ArrayList<MovieDetailModel>

    @Insert
    fun insert(vararg movieDetailModel: MovieDetailModel)
}