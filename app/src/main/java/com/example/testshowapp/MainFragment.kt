package com.example.testshowapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testshowapp.common.Common
import com.example.testshowapp.models.Show
import com.example.testshowapp.models.ShowPojo
import com.example.testshowapp.retrofit.RetrofitServices
import com.example.testshowapp.roomDB.RoomDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var itemDecoration: RecyclerView.ItemDecoration
    private lateinit var ShowListAdapter: ShowListAdapter
    private lateinit var searchView: SearchView
    private lateinit var mRetrofitService: RetrofitServices
    private var ShowList: MutableList<ShowPojo> = mutableListOf()
    private lateinit var  database: RoomDB
    private var ShowListToAdapter: MutableList<Show> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = RoomDB.getDatabaseInstance(context!!.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        //Find all required views
        mainRecyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)
        searchView = view.findViewById(R.id.search_view)
        //Set up View to RecyclerView
        ShowListAdapter = ShowListAdapter(context!!,ShowListToAdapter)
        mainRecyclerView.adapter = ShowListAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(activity)
        itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        mainRecyclerView.addItemDecoration(itemDecoration)
        //Create retrofitService
        mRetrofitService = Common.retrofitService
        //Set listener to Search TVShow
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchJson(query)
                    return true
                } else {
                    return false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ShowListAdapter.filter.filter(newText)
                return false
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun fetchJson(userQueryWord: String) {
        mRetrofitService.getData(userQueryWord).enqueue(object : Callback<List<ShowPojo>> {
            override fun onFailure(call: Call<List<ShowPojo>>, t: Throwable) {
                Toast.makeText(activity!!, "Check the connection!", Toast.LENGTH_SHORT)
                    .show()
                println(call.request().url().toString())
            }

            override fun onResponse(
                call: Call<List<ShowPojo>>,
                response: Response<List<ShowPojo>>
            ) {
                //Checking response
                if (response.code() != 200) {
                    Toast.makeText(activity, "Request failure!", Toast.LENGTH_SHORT)
                        .show()
                } else if (response.body()!!.isEmpty()) {
                    Toast.makeText(activity, "No Results!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    database.clearAllTables()
                    ShowList.clear()
                    ShowList = response.body()!!.toMutableList()
                    for (it in ShowList){
                        database.FavoritesDao().insert(it.show)
                    }
                    ShowListToAdapter = database.FavoritesDao().getAll() as MutableList<Show>
                    ShowListAdapter = ShowListAdapter(context!!,ShowListToAdapter)
                    mainRecyclerView.adapter = ShowListAdapter
                }
            }
        })
    }
}
