package com.priyank.findrestaurants.network

import com.priyank.findrestaurants.model.Reviews
import com.priyank.findrestaurants.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {

    @GET("businesses/search")
    suspend fun getByLocation(
        @Query("location") location: String,
        @Query("categories") cat: String,
        @Query("limit") limit: Int
    ): SearchResult

    @GET("businesses/{id}/reviews")
    suspend fun getReviews(
        @Path("id") id: String?
    ): Reviews

    @GET("businesses/search")
    suspend fun getByTerm(
        @Query("term") term: String,
        @Query("location") location: String,
        @Query("categories") cat: String,
        @Query("limit") limit: Int
    ): SearchResult
}