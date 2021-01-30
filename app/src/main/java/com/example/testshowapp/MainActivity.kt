package com.example.testshowapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testshowapp.common.Common
import com.example.testshowapp.model.ShowPojo
import com.example.testshowapp.retrofit.RetrofitServices
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_favorite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.Locale.filter


class MainActivity : AppCompatActivity() {
    private var mainFragment = MainFragment()
    private var favoriteFragment = FavoriteFragment()
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mainDrawerLayout: DrawerLayout
    private lateinit var mainNavView: NavigationView
    private var toolBar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Find all required views
        mainDrawerLayout = findViewById<DrawerLayout>(R.id.mainDrawerLayout)
        mainNavView = findViewById<NavigationView>(R.id.mainNavView)
        toolBar = findViewById<Toolbar>(R.id.includeToolbar)
        //Set up ActionBar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Main"
        //Set up toggle to ActionBar
        toggle = ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close)
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //Set up navigation ability to mainNavView
        setupDrawerContent(mainNavView)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, mainFragment)
            commit()
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

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                selectDrawerItem(item)
                return true
            }
        })
    }

    fun selectDrawerItem(menuItem: MenuItem) {
        lateinit var fragment: Fragment
        fragment = when (menuItem.itemId) {
            R.id.mainItem -> mainFragment
            R.id.favoritesItem -> favoriteFragment
            else -> mainFragment
        }
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit()

        menuItem.setChecked(true)

        supportActionBar?.title = menuItem.title

        mainDrawerLayout.closeDrawer(GravityCompat.START)
    }


}


