package com.priyank.findrestaurants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.priyank.findrestaurants.R
import com.priyank.findrestaurants.activity.FavoritesActivity
import com.priyank.findrestaurants.model.Favorite
import com.priyank.findrestaurants.other.PrefRepository
import kotlinx.android.synthetic.main.item_fav_restaurant.view.*

class FavoriteResAdapter(val context: Context, val favRestaurants: ArrayList<Favorite>) :
    RecyclerView.Adapter<FavoriteResAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_fav_restaurant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = favRestaurants[position]
        holder.bind(favorite)
    }

    override fun getItemCount() = favRestaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite) {
            itemView.tvName.text = favorite.favName
            itemView.tvAddress.text = favorite.favAdd
            Glide
                .with(context)
                .load(favorite.favImg)
                .centerCrop()
                .placeholder(R.drawable.bg_restaurant)
                .into(itemView.ivImage)
            itemView.ivFavorite.setOnClickListener() {
                Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show()
                favRestaurants.remove(
                    Favorite(
                        favorite.favId,
                        favorite.favName,
                        favorite.favImg,
                        favorite.favAdd
                    )
                )
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, favRestaurants.size)
                val pref = PrefRepository(context)
                pref.savePrefData(favRestaurants)
            }
        }
    }
}