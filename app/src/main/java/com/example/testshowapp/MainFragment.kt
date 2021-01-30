package com.example.testshowapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testshowapp.common.Common
import com.example.testshowapp.model.ShowPojo
import com.example.testshowapp.retrofit.RetrofitServices
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
 const val SHOW_LIST_KEY = "ShowList"
class MainFragment : Fragment() {
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var itemDecoration: RecyclerView.ItemDecoration
    private lateinit var ShowListAdapter: ShowListAdapter
    private lateinit var searchView: SearchView
    private lateinit var mRetrofitService: RetrofitServices
    private var ShowList: MutableList<ShowPojo> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_main, container, false)
        //Find all required views
        mainRecyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)
        searchView = view.findViewById(R.id.search_view)
        //Set up View to RecyclerView
        ShowListAdapter = ShowListAdapter(context!!, ShowList)
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
                    return fetchJson(query)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    fun fetchJson(userQueryWord: String): Boolean {

        var isRequestSuccessful = true

        mRetrofitService.getData(userQueryWord).enqueue(object : Callback<List<ShowPojo>> {
            override fun onFailure(call: Call<List<ShowPojo>>, t: Throwable) {
                Toast.makeText(activity!!, "Failure request!", Toast.LENGTH_SHORT)
                    .show()
                println(call.request().url().toString())
                isRequestSuccessful = false
            }

            override fun onResponse(
                call: Call<List<ShowPojo>>,
                response: Response<List<ShowPojo>>
            ) {
                //Checking response
                if (response.code() != 200) {
                    Toast.makeText(activity, "Check the connection!", Toast.LENGTH_SHORT)
                        .show()
                    isRequestSuccessful = false
                } else if (response.body()!!.isEmpty()) {
                    Toast.makeText(activity, "No Results!", Toast.LENGTH_SHORT)
                        .show()
                    isRequestSuccessful = false
                } else {
                    ShowList.clear()
                    ShowList = response.body()!!.toMutableList()
                    println(call.request().url().toString())

                    ShowListAdapter = ShowListAdapter(context!!, ShowList)
                    mainRecyclerView.adapter = ShowListAdapter
                    isRequestSuccessful = true
                }
            }

        })
        return isRequestSuccessful
    }


}
