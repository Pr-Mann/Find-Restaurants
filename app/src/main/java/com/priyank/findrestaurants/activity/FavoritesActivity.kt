package com.priyank.findrestaurants.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priyank.findrestaurants.R
import com.priyank.findrestaurants.adapter.FavoriteResAdapter
import com.priyank.findrestaurants.model.Favorite
import com.priyank.findrestaurants.other.Global
import com.priyank.findrestaurants.other.PrefRepository
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_favorites.*
import java.lang.reflect.Type

class FavoritesActivity : AppCompatActivity() {

    var favRestaurants: ArrayList<Favorite>? = null
    lateinit var adapter: FavoriteResAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        loadData()
    }

    private fun loadData() {
        val pref = PrefRepository(this)
        favRestaurants = pref.getPrefData()
        if (favRestaurants == null) {
            favRestaurants = ArrayList()
            tvMsg.visibility = View.VISIBLE
            llResult.visibility = View.GONE
        } else {
            if (favRestaurants!!.isEmpty()) {
                tvMsg.visibility = View.VISIBLE
                llResult.visibility = View.GONE
                return
            }
            setAdapter()
        }
    }

    fun setAdapter() {
        adapter = FavoriteResAdapter(this, favRestaurants!!)
        rvFavRestaurants.adapter = adapter
        rvFavRestaurants.layoutManager = LinearLayoutManager(this)
        tvMsg.visibility = View.GONE
        llResult.visibility = View.VISIBLE
    }
}