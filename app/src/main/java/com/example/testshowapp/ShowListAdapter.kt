package com.example.testshowapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_show.view.*

class ShowListAdapter(var showList: List<TVShowStruct>) :
    RecyclerView.Adapter<ShowListAdapter.TVShowHolder>() {
    inner class TVShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return TVShowHolder(view)
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    //Binds a data to items
    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {

        holder.itemView.apply {
            tvShowName.text = showList[position].tvShowStructName
            itemCheckBox.isChecked = showList[position].isFavorite
        }
    }
}