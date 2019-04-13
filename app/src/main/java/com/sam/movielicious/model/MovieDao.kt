package com.sam.movielicious.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface MovieDao {
    @Query("DELETE  FROM MovieDetailModel Where id = :id and favorite = :favorite ")
    fun deleteModel(id: Int,favorite:Boolean)


    @Query("SELECT * FROM MovieDetailModel Where id = :id and favorite = :favorite ")
    fun getModel(id: Int,favorite:Boolean): MovieDetailModel


    @get:Query("SELECT * FROM MovieDetailModel ")
    val loadAllModels: List<MovieDetailModel>


    @Insert
    fun insert(vararg movieDetailModel: MovieDetailModel)
}