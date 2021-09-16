package com.priyank.findrestaurants.model

data class SearchResult(
    val businesses: ArrayList<Restaurant>
)

data class Restaurant(
    val name: String? = null,
    val image_url: String? = null,
    val id: String? = null,
    val location: Address
)

data class Address(
    val address1: String? = null,
    val city: String? = null, val state: String? = null,
    val zip_code: String? = null,
    val country: String? = null
)

data class Reviews(
    val reviews: List<Review>
)

data class Review(
    val rating: Int? = null,
    val text: String? = null,
    val user: User
)

data class User(
    val image_url: String? = null,
    val name: String? = null
)

data class Favorite(
    val favId: String? = null,
    val favName: String? = null,
    val favImg: String? = null,
    val favAdd: String?
)