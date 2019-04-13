package com.sam.movielicious.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sam.movielicious.R
import com.sam.movielicious.utils.extension.getParentActivity


@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}
@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.getContext())
        .load(imageUrl)
        .placeholder(R.drawable.loading)
        .into(view)

}

@BindingAdapter("textcolor") //customise your name here
fun setTextColor(view: TextView, vote: String) {
    var color = "#800000"
    val x = vote.substring(0,3).toFloat()
    when (x){
        in 1..2  -> color = "#ff7f7f"
        in 2..3  -> color = "#ff4c4c"
        in 3..4  -> color = "#ff1919"
        in 4..5  -> color = "#ffd27f"
        in 5..6  -> color = "#ffc04c"
        in 6..7  -> color = "#ffef99"
        in 7..8  -> color = "#ffe766"
        in 8..9  -> color = "#ffdf32"
        in 9..10  -> color = "#FFD700"
    }
    view.setTextColor(Color.parseColor(color))
}
