package com.example.testshowapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testshowapp.model.ShowPojo
import kotlinx.android.synthetic.main.item_show.view.*


const val RATING = "Rating: "
const val GENRES = "Genres: "

class ShowListAdapter(private val context: Context, var showList: List<ShowPojo>) :
    RecyclerView.Adapter<ShowListAdapter.TVShowHolder>() {
    inner class TVShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return TVShowHolder(view)
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    //Binds a data from saved structure to

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {

        holder.itemView.apply {
                tvShowName.text = showList[position].show.name
                itemCheckBox.isChecked = showList[position].show.isFavorite
                rating.text = (RATING + showList[position].show.rating?.average?.toInt())
                tvShowDescription.text = GENRES + showList[position].show.genres?.joinToString()
                Glide.with(context)
                    .load(showList[position].show.image?.original?.replace("http", "https"))
                    .fitCenter().diskCacheStrategy(
                        DiskCacheStrategy.RESOURCE
                    ).into(TvShowImageView)

        }
    }
}
