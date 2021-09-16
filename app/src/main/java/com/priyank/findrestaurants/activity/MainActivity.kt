package com.priyank.findrestaurants.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.priyank.findrestaurants.R
import com.priyank.findrestaurants.adapter.RestaurantAdapter
import com.priyank.findrestaurants.model.Restaurant
import com.priyank.findrestaurants.model.SearchResult
import com.priyank.findrestaurants.other.Global
import com.priyank.findrestaurants.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.llResult
import kotlinx.android.synthetic.main.activity_main.tvMsg

class MainActivity : AppCompatActivity() {

    val restaurants = mutableListOf<Restaurant>()
    lateinit var adapter: RestaurantAdapter
    lateinit var viewModel: MainViewModel

    val cat = "restaurants"
    val location = "Canada"
    val limit = 10
    var isAsc: Boolean = true
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setAdapter()
        if (Global.checkNet(this)) {
            getData()
        } else {
            tvMsg.text = getString(R.string.no_internet)
            tvMsg.visibility = View.VISIBLE
            llResult.visibility = View.GONE
            cvRefresh.visibility = View.VISIBLE
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }

        ivAscDesc.setOnClickListener() {
            if (isAsc) {
                isAsc = false
                ivAscDesc.setImageResource(R.drawable.ic_desc)
                restaurants.sortByDescending { it.name }
                adapter.notifyDataSetChanged()
            } else {
                isAsc = true
                ivAscDesc.setImageResource(R.drawable.ic_asc)
                restaurants.sortBy { it.name }
                adapter.notifyDataSetChanged()
            }
        }

        cvRefresh.setOnClickListener() {
            if (Global.checkNet(this)) {
                getData()
            } else {
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun setAdapter() {
        adapter = RestaurantAdapter(this, restaurants)
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = GridLayoutManager(this, 2)
    }

    fun init() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    fun getData() {
        viewModel.getSearchResultObserver().observe(this, Observer<SearchResult> { it ->
            if (it != null) {
                restaurants.clear()
                tvMsg.visibility = View.GONE
                llResult.visibility = View.VISIBLE
                cvRefresh.visibility = View.GONE
                restaurants.addAll(it.businesses)
                restaurants.sortBy { it.name }
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.getRestaurantsByLocation(location, cat, limit)
    }

    fun getDataByTerm(term: String) {
        viewModel.getSearchResultObserver().observe(this, Observer<SearchResult> { it ->
            if (it != null) {
                restaurants.clear()
                tvMsg.visibility = View.GONE
                llResult.visibility = View.VISIBLE
                cvRefresh.visibility = View.GONE
                restaurants.addAll(it.businesses)
                if (restaurants.isEmpty()) {
                    tvMsg.text = getString(R.string.no_data)
                    tvMsg.visibility = View.VISIBLE
                    llResult.visibility = View.GONE
                } else {
                    restaurants.sortBy { it.name }
                    adapter.notifyDataSetChanged()
                }
            }
        })
        viewModel.getRestaurantsByTerm(term, location, cat, limit)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (Global.checkNet(this@MainActivity)) {
                    if (query != null) {
                        getDataByTerm(query)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.no_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.onActionViewCollapsed()
        } else {
            super.onBackPressed()
        }
    }
}