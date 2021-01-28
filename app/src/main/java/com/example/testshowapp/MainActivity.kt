package com.example.testshowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testshowapp.common.Common
import com.example.testshowapp.model.Show
import com.example.testshowapp.model.ShowPojo
import com.example.testshowapp.retrofit.RetrofitServices
import com.google.android.material.navigation.NavigationView
import retrofit2.*


class MainActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mainDrawerLayout: DrawerLayout
    private lateinit var mainNavView: NavigationView
    private var toolBar: Toolbar? = null
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var itemDecoration: RecyclerView.ItemDecoration
    private lateinit var ShowListAdapter: ShowListAdapter
    private lateinit var searchView: SearchView
    private lateinit var mRetrofitService: RetrofitServices
    private var ShowList: MutableList<ShowPojo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Find all required views
        mainDrawerLayout = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        mainNavView = findViewById<NavigationView>(R.id.mainNavView)
        toolBar = findViewById<Toolbar>(R.id.includeToolbar)
        mainRecyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)
        searchView = findViewById(R.id.search_view)
        //Set up ActionBar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Main"
        //Set up toggle to ActionBar
        toggle = ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //Set up View to RecyclerView
        ShowListAdapter = ShowListAdapter(applicationContext, ShowList)
        mainRecyclerView.adapter = ShowListAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mainRecyclerView.addItemDecoration(itemDecoration)

        mRetrofitService = Common.retrofitService


        //Search TVShow
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    return fetchJson(query)
                } else {
                    return false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false

            }

        })

    }

    override fun onBackPressed() {

        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun fetchJson(userQueryWord: String): Boolean {

        var isRequestSuccessful = true

        mRetrofitService.getData(userQueryWord).enqueue(object : Callback<List<ShowPojo>> {
            override fun onFailure(call: Call<List<ShowPojo>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failure request!", Toast.LENGTH_SHORT)
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
                    Toast.makeText(applicationContext, "Check the connection!", Toast.LENGTH_SHORT)
                        .show()
                    isRequestSuccessful = false
                } else if (response.body()!!.isEmpty()) {
                    Toast.makeText(applicationContext, "No Results!", Toast.LENGTH_SHORT)
                        .show()
                    isRequestSuccessful = false
                } else {
                    ShowList.clear()
                    ShowList = response.body()!!.toMutableList()
                    println(call.request().url().toString())

                    ShowListAdapter = ShowListAdapter(applicationContext, ShowList)
                    mainRecyclerView.adapter = ShowListAdapter
                    isRequestSuccessful = true
                }
            }

        })

        return isRequestSuccessful
    }
}
