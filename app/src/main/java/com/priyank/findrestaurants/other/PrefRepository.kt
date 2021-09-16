package com.priyank.findrestaurants.other

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priyank.findrestaurants.model.Favorite
import java.lang.reflect.Type

class PrefRepository(val context: Context) {
    lateinit var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
            "favRestaurants",
            AppCompatActivity.MODE_PRIVATE
        )

    }

    fun getPrefData(): ArrayList<Favorite>? {
        val gson = Gson()
        val json = sharedPreferences.getString("favRestaurant", null)
        val type: Type = object : TypeToken<ArrayList<Favorite?>?>() {}.type
        var favRestaurants: ArrayList<Favorite>? = gson.fromJson(json, type)
        if (favRestaurants == null) {
            favRestaurants = ArrayList()
        }
        return favRestaurants
    }
    fun savePrefData(favRestaurants: ArrayList<Favorite>?){
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val favId = gson.toJson(favRestaurants)
        editor.putString("favRestaurant", favId)
        editor.apply()
    }
}