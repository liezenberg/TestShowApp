package com.example.testshowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mainDrawerLayout: DrawerLayout
    private lateinit var mainNavView: NavigationView
    private var toolBar: Toolbar? = null
    private lateinit var mainRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainDrawerLayout = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        mainNavView = findViewById<NavigationView>(R.id.mainNavView)
        toolBar = findViewById<Toolbar>(R.id.includeToolbar)
        mainRecyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)

        var HardCodedShowList = mutableListOf(
            TVShowStruct("Itm1", false),
            TVShowStruct("Itm2", false),
            TVShowStruct("Itm3", false),
            TVShowStruct("Itm4", false),
            TVShowStruct("Itm5", false),
            TVShowStruct("Itm6", false),
            TVShowStruct("Itm7", false),
            TVShowStruct("Itm8", false),
            TVShowStruct("Itm9", false),
            TVShowStruct("Itm10", false)
        )

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Main"

        toggle = ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val ShowListAdapter = ShowListAdapter(HardCodedShowList)
        mainRecyclerView.adapter = ShowListAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(this)


        mainNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mainItem -> Toast.makeText(
                    applicationContext,
                    "Already in Main page!",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.favoritesItem -> Toast.makeText(
                    applicationContext,
                    "Clicked Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
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



}