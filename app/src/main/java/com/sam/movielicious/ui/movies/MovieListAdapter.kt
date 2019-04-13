package com.sam.movielicious.ui.movies

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sam.movielicious.R
import com.sam.movielicious.databinding.MovieItemBinding
import com.sam.movielicious.model.MovieListItemModel
import com.sam.movielicious.ui.Handler

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private var movieList=ArrayList<MovieListItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: com.sam.movielicious.databinding.MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
        holder.itemView.setOnClickListener {
            holder.handler.onMovieClick(holder.itemView,movieList[position].id.toString(),
                movieList[position].title)
        }
    }

    override fun getItemCount(): Int {
        return  movieList.size
    }

    fun updateMovieList(movieList:List<MovieListItemModel>){

        this.movieList.addAll(movieList)
        notifyDataSetChanged()

    }
    fun clearList(){
        this.movieList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = MovieItemViewModel()
        val handler = Handler()

        fun bind(movieListItemModel:MovieListItemModel){
            viewModel.bind(movieListItemModel)

            binding.handlers = handler
            binding.viewModel = viewModel

        }

    }
}