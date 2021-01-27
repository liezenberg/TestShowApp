package com.example.testshowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testshowapp.common.Common
import com.example.testshowapp.model.ModelsForGson
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
    private lateinit var searchButton: View
    private lateinit var searchEditText: EditText
    private lateinit var mRetrofitService: RetrofitServices
    private var ShowList: MutableList<ModelsForGson> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Find all required views
        mainDrawerLayout = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        mainNavView = findViewById<NavigationView>(R.id.mainNavView)
        toolBar = findViewById<Toolbar>(R.id.includeToolbar)
        mainRecyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)
        searchButton = findViewById(R.id.search_button)
        searchEditText = findViewById<EditText>(R.id.search_edit_text)
        //Set up ActionBar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Main"
        //Set up toggle to ActionBar
        toggle = ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //Set up View to RecyclerView
        ShowListAdapter = ShowListAdapter(ShowList)
        mainRecyclerView.adapter = ShowListAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mainRecyclerView.addItemDecoration(itemDecoration)

        mRetrofitService = Common.retrofitService


        //Search TVShow
        searchButton.setOnClickListener {
            var userQuery = searchEditText.text.toString()
            fetchJson(userQuery)
        }

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

    fun fetchJson(userQueryWord: String) {

        mRetrofitService.getData(userQueryWord).enqueue(object : Callback<List<ModelsForGson>> {
            override fun onFailure(call: Call<List<ModelsForGson>>, t: Throwable) {
                println(t.localizedMessage)
                Toast.makeText(applicationContext, "Failure request!", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<List<ModelsForGson>>,
                response: Response<List<ModelsForGson>>
            ) {
                //Checking response
                if (response.code() != 200) {
                    Toast.makeText(applicationContext, "Check the connection!", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                else{

                    ShowList = response.body()!!.toMutableList()
                    Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT)
                        .show()
                    //TODO("Delete previous Adapter")
                    ShowListAdapter = ShowListAdapter(ShowList)
                    mainRecyclerView.adapter = ShowListAdapter
                    mainRecyclerView.adapter!!.notifyDataSetChanged()
                }
            }
        })


    }
}
