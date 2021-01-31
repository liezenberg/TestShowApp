package com.example.testshowapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testshowapp.common.Common
import com.example.testshowapp.models.Show
import com.example.testshowapp.models.ShowPojo
import com.example.testshowapp.retrofit.RetrofitServices
import com.example.testshowapp.roomDB.RoomDB

class FavoriteFragment : Fragment() {
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var itemDecoration: RecyclerView.ItemDecoration
    private lateinit var favoriteShowListAdapter: FavoriteShowAdapter
    private lateinit var favoriteSearchView: SearchView
    private var favoriteList: MutableList<Show> = mutableListOf()
    private lateinit var  database: RoomDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = RoomDB.getDatabaseInstance(context!!)
        favoriteList = database.FavoritesDao().getFavorites() as MutableList<Show>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_favorite, container, false)
        //Find all required views
        favoriteRecyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        favoriteSearchView = view.findViewById(R.id.favorite_search_view)
        //Set up View to RecyclerView
        favoriteShowListAdapter = FavoriteShowAdapter(context!!, favoriteList)
        favoriteRecyclerView.adapter = favoriteShowListAdapter
        favoriteRecyclerView.layoutManager = LinearLayoutManager(activity)
        itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        favoriteRecyclerView.addItemDecoration(itemDecoration)
        //Set listener to Search TVShow
        favoriteSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                favoriteShowListAdapter.filter.filter(newText)
                return false
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}