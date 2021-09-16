package com.priyank.findrestaurants.network

import com.priyank.findrestaurants.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroInstance {

    companion object {
        private val BASE_URL: String = "https://api.yelp.com/v3/"

        private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.API_KEY}"
                )
                .build()
            chain.proceed(newRequest)
        }.build()

        fun getRetroInstancec(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
