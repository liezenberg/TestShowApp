package com.example.testshowapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testshowapp.models.Show
import com.example.testshowapp.roomDB.RoomDB
import kotlinx.android.synthetic.main.item_show.view.*


const val RATING = "Rating: "
const val GENRES = "Genres: "

class ShowListAdapter(
    private val context: Context,
    var showList: MutableList<Show>
) :
    RecyclerView.Adapter<ShowListAdapter.TVShowHolder>(), Filterable {

    inner class TVShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var database: RoomDB = RoomDB.getDatabaseInstance(context)

    //List of Show objects to perform filtering
    var searchItemsList: MutableList<Show> = showList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return TVShowHolder(view)
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {
        holder.itemView.apply {
            tvShowName.text = showList[position].name
            itemCheckBox.isChecked = showList[position].isFavorite
            if (showList[position].rating != null) {
                rating.text = (RATING + showList[position].rating?.average?.toInt())
            }
            if (showList[position].genres != null) {
                tvShowDescription.text = showList[position].genres?.joinToString(", ", GENRES)
            }
            //Insert image into view holder
            Glide.with(context)
                .load(showList[position].image?.original?.replace("http", "https"))
                .fitCenter().diskCacheStrategy(
                    DiskCacheStrategy.RESOURCE
                ).into(TvShowImageView)
            //Set callback to checkbox
            holder.itemView.itemCheckBox.setOnClickListener {
                if (holder.itemView.itemCheckBox.isChecked) {
                    database.FavoritesDao()
                        .update(showList[position].ID, holder.itemView.itemCheckBox.isChecked)
                    showList.clear()
                    searchItemsList.clear()
                    showList.addAll(database.FavoritesDao().getAll() as MutableList<Show>)
                    searchItemsList.addAll(database.FavoritesDao().getAll())
                    //notifyDataSetChanged()
                } else {
                    database.FavoritesDao()
                        .update(showList[position].ID, holder.itemView.itemCheckBox.isChecked)
                    showList.clear()
                    showList.addAll(database.FavoritesDao().getAll() as MutableList<Show>)
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return adapterFilter()
    }

    //Filter to perform filtering displayed recyclerview
    private inner class adapterFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            searchItemsList = database.FavoritesDao().getAll() as MutableList<Show>
            var filteredViews: MutableList<Show> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredViews = searchItemsList.toMutableList()
            } else {
                var filterPattern = constraint.toString().toLowerCase().trim()
                for (it in searchItemsList) {
                    if (it.name != null && it.name!!.toLowerCase()
                            .startsWith(filterPattern)
                    ) {
                        filteredViews.add(it)
                    }
                }
            }
            var results = FilterResults()
            results.values = filteredViews
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            showList.clear()
            showList.addAll(results?.values as MutableList<Show>)
            notifyDataSetChanged()
        }
    }

}