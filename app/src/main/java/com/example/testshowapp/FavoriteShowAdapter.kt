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
import com.example.testshowapp.common.GENRES
import com.example.testshowapp.common.RATING
import com.example.testshowapp.models.Show
import com.example.testshowapp.roomDB.RoomDB
import kotlinx.android.synthetic.main.item_show.view.*

class FavoriteShowAdapter(
    private val context: Context,
    var showList: MutableList<Show>
) :
    RecyclerView.Adapter<FavoriteShowAdapter.FavoriteTVShowHolder>(), Filterable {
    inner class FavoriteTVShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var database: RoomDB
    var searchItemsList: MutableList<Show> = showList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        database = RoomDB.getDatabaseInstance(context)
        return FavoriteTVShowHolder(view)
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteShowAdapter.FavoriteTVShowHolder, position: Int) {

        holder.itemView.apply {
            tvShowName.text = showList[position].name
            itemCheckBox.isChecked = showList[position].isFavorite
            if (showList[position].rating?.average != null) {
                rating.text = (RATING + showList[position].rating?.average?.toInt())
            }
            tvShowDescription.text = GENRES + showList[position].genres?.joinToString()
            Glide.with(context)
                .load(showList[position].image?.original?.replace("http", "https"))
                .fitCenter().diskCacheStrategy(
                    DiskCacheStrategy.RESOURCE
                ).into(TvShowImageView)
        }
        holder.itemView.itemCheckBox.setOnClickListener {
            if (!holder.itemView.itemCheckBox.isChecked) {
                database.FavoritesDao()
                    .update(showList[position].ID, holder.itemView.itemCheckBox.isChecked)
                showList.removeAt(holder.position)
                notifyItemRemoved(holder.position)
                notifyItemRangeChanged(holder.position, showList.size)
            }
        }

    }

    override fun getFilter(): Filter {
        return adapterFilter
    }

    private var adapterFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            searchItemsList = database.FavoritesDao().getFavorites() as MutableList<Show>
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
