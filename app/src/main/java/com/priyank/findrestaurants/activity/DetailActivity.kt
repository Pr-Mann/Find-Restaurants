package com.priyank.findrestaurants.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.priyank.findrestaurants.R
import com.priyank.findrestaurants.adapter.ReviewAdapter
import com.priyank.findrestaurants.model.Favorite
import com.priyank.findrestaurants.model.Review
import com.priyank.findrestaurants.model.Reviews
import com.priyank.findrestaurants.other.Global
import com.priyank.findrestaurants.other.PrefRepository
import com.priyank.findrestaurants.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    val review = mutableListOf<Review>()
    var favRestaurants: ArrayList<Favorite>? = null
    lateinit var adapter: ReviewAdapter
    var isFavorite: Boolean = false
    lateinit var viewModel: DetailViewModel
    var id: String? = ""
    var imgUrl: String? = ""
    var name: String? = ""
    var address: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val i = intent.extras
        id = i?.getString("Id")
        imgUrl = i?.getString("ImgUrl")
        name = i?.getString("Name")
        address = i?.getString("Address")
        init()
        setAdapter()
        loadData()

        Glide
            .with(this)
            .load(imgUrl)
            .centerCrop()
            .placeholder(R.drawable.bg_restaurant)
            .into(ivImage)

        tvName.text = name
        tvAddress.text = address

        if (Global.checkNet(this)) {
            getData(id)
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        ivFavorite.setOnClickListener() {
            if (isFavorite) {
                isFavorite = false
                ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                Toast.makeText(this, getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT)
                    .show()
                favRestaurants?.remove(
                    Favorite(
                        id,
                        name,
                        imgUrl,
                        address
                    )
                )
                saveData()
            } else {
                isFavorite = true
                ivFavorite.setImageResource(R.drawable.ic_favorite)
                Toast.makeText(this, getString(R.string.added_to_favorite), Toast.LENGTH_SHORT)
                    .show()
                favRestaurants?.add(
                    Favorite(
                        id,
                        name,
                        imgUrl,
                        address
                    )
                )
                saveData()
            }
        }
    }

    fun init() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    fun setAdapter() {
        adapter = ReviewAdapter(this, review)
        rvReviews.adapter = adapter
        rvReviews.layoutManager = LinearLayoutManager(this)
    }

    fun getData(id: String?) {
        viewModel.getReviewObserver().observe(this, Observer<Reviews> { it ->
            if (it != null) {
                review.clear()
                review.addAll(it.reviews)
                adapter.notifyDataSetChanged()
            }
        })
        if (id != null) {
            viewModel.getReviewById(id)
        }
    }

    private fun loadData() {
        val pref = PrefRepository(this)
        favRestaurants = pref.getPrefData()
        for (i in favRestaurants!!) {
            if (i.favId == id) {
                isFavorite = true
                ivFavorite.setImageResource(R.drawable.ic_favorite)
                return
            }
        }
    }

    private fun saveData() {
        val pref = PrefRepository(this)
        pref.savePrefData(favRestaurants)
    }
}