package com.sam.movielicious.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "movieDetailModel")
data class MovieDetailModel(
    var adult: Boolean?,
    var backdrop_path: String?,
    @Ignore
    var belongs_to_collection: Any?,
    var budget: Int?,
    @Ignore
    var genres: List<Genre>?,
    var homepage: String?,
    @field:PrimaryKey
    var id: Int ,
    var imdb_id: String?,
    var original_language: String?,
    var original_title: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    @Ignore
    var production_companies: List<ProductionCompany>?,
    @Ignore
    var production_countries: List<ProductionCountry>?,
    var release_date: String?,
    var revenue: Int?,
    var runtime: Int?,
    @Ignore
    var spoken_languages: List<SpokenLanguage>?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var vote_average: Double?,
    var vote_count: Int?,
    var favorite: Boolean= false
){
    constructor():this(null,"0",0.0,null,null,null,
        -1,"","",null,null,null,
        null,null,
        null,null,
        null,null,null,
        null,null,null,null,null,null,false)
}