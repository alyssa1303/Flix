package com.example.flixster

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    // Cheap operation: simply bind data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder, position: $position")
        val movie = movies[position]
        holder.bin(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val tvPoster = itemView.findViewById<ImageView>(R.id.tvPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)


        fun bin(movie: Movie) {
            var imageUrl = ""
            var orientation = context.getResources().getConfiguration().orientation

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imageUrl = movie.posterImageUrl
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.backdropImageUrl
                Log.i(TAG, "backdropSize $imageUrl")
            }

            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            Glide.with(context)
                .load(imageUrl)
                .into(tvPoster)
        }

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            // 1. Get notified of which movie was tapped on
            val movie = movies[adapterPosition]
            // Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()

            // 2. Launch intent to navigate to the new activity view
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            // val p1 = Pair.create(tvPoster as View?, "poster")
            // val p2 = Pair.create(tvTitle as View?, "title")
            // val p3 = Pair.create(tvOverview as View?, "overview")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, itemView, "movie")
            context.startActivity(intent, options.toBundle())
        }

    }

}
