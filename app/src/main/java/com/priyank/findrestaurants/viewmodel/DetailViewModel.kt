package com.priyank.findrestaurants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyank.findrestaurants.model.Favorite
import com.priyank.findrestaurants.model.Reviews
import com.priyank.findrestaurants.network.RetroInstance
import com.priyank.findrestaurants.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    var reviewsLiveData: MutableLiveData<Reviews> = MutableLiveData()

    fun getReviewObserver(): MutableLiveData<Reviews> {
        return reviewsLiveData
    }

    fun getReviewById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetroInstancec().create(RetroService::class.java)
            val response = retroInstance.getReviews(id)
            reviewsLiveData.postValue(response)
        }
    }
}