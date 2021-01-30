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
import com.example.testshowapp.model.ShowPojo
import kotlinx.android.synthetic.main.item_show.view.*
import java.util.Collections.addAll


const val RATING = "Rating: "
const val GENRES = "Genres: "

class ShowListAdapter(
    private val context: Context,
    var showList: MutableList<ShowPojo>
) :
    RecyclerView.Adapter<ShowListAdapter.TVShowHolder>(), Filterable {
    inner class TVShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var searchItemsList: MutableList<ShowPojo> = showList.toMutableList()

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
            if (showList[position].show.rating?.average != null) {
                rating.text = (RATING + showList[position].show.rating?.average?.toInt())
            }
            tvShowDescription.text = GENRES + showList[position].show.genres?.joinToString()
            Glide.with(context)
                .load(showList[position].show.image?.original?.replace("http", "https"))
                .fitCenter().diskCacheStrategy(
                    DiskCacheStrategy.RESOURCE
                ).into(TvShowImageView)
        }
    }

    override fun getFilter(): Filter {
        return adapterFilter
    }

    private var adapterFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredViews: MutableList<ShowPojo> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredViews = searchItemsList.toMutableList()
            } else {
                var filterPattern = constraint.toString().toLowerCase().trim()
                for (it in searchItemsList) {
                    if (it.show.name != null && it.show.name!!.toLowerCase()
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
            showList.addAll(results?.values as MutableList<ShowPojo>)
            notifyDataSetChanged()
        }

    }

}
