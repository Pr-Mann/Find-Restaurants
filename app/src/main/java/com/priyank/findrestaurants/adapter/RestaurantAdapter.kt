package com.priyank.findrestaurants.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyank.findrestaurants.R
import com.priyank.findrestaurants.activity.DetailActivity
import com.priyank.findrestaurants.activity.MainActivity
import com.priyank.findrestaurants.model.Restaurant
import com.priyank.findrestaurants.other.Global
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantAdapter(val context: Context, val restaurants: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant) {
            itemView.tvName.text = restaurant.name
            itemView.tvAddress.text = restaurant.location.address1
            Glide
                .with(context)
                .load(restaurant.image_url)
                .centerCrop()
                .placeholder(R.drawable.bg_restaurant)
                .into(itemView.ivImage)
            itemView.cvRestaurant.setOnClickListener() {
                if (Global.checkNet(context)) {

                    val address =
                        "${restaurant.location.address1}, ${restaurant.location.city}, ${restaurant.location.state}, ${restaurant.location.zip_code}, ${restaurant.location.country}"
                    val i = Intent(context, DetailActivity::class.java)
                    i.putExtra("Id",restaurant.id)
                    i.putExtra("ImgUrl",restaurant.image_url)
                    i.putExtra("Name",restaurant.name)
                    i.putExtra("Address",address)
                    (context as MainActivity).startActivity(i)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}