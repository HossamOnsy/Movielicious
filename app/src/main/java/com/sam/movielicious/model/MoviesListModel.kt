package com.sam.movielicious.model

data class MoviesListModel(
    val page: Int,
    val results : List<MovieListItemModel>,
    val total_pages: Int,
    val total_results: Int
)