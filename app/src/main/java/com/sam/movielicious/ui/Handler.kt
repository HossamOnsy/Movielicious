package com.sam.movielicious.ui

import android.content.Intent
import android.view.View
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.sam.movielicious.MovieDetailsActivity

class Handler(){


    fun onMovieClick (view: View, id:String,movieTitle:String){
        val intent = Intent(view.context, MovieDetailsActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("movieTitle",movieTitle);
        view.context.startActivity(intent)
        Animatoo.animateFade(view.context);
    }

}