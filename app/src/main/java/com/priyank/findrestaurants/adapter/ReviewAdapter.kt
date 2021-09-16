package com.priyank.findrestaurants.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyank.findrestaurants.R
import com.priyank.findrestaurants.model.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter(val context: Context, val review: MutableList<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = review[position]
        holder.bind(review)
    }

    override fun getItemCount() = review.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: Review) {
            Glide
                .with(context)
                .load(review.user.image_url)
                .centerCrop()
                .placeholder(R.drawable.user)
                .into(itemView.ivProfile)
            itemView.tvName.text = review.user.name
            itemView.rbRating.rating = review.rating?.toFloat() ?: 1.0f
            itemView.tvReview.text = "\"${review.text?.replace("\n"," ")}\""
        }
    }
}