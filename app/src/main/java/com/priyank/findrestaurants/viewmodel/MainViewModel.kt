package com.priyank.findrestaurants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyank.findrestaurants.model.SearchResult
import com.priyank.findrestaurants.network.RetroInstance
import com.priyank.findrestaurants.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var searchResultLiveData: MutableLiveData<SearchResult> = MutableLiveData()

    fun getSearchResultObserver(): MutableLiveData<SearchResult> {
        return searchResultLiveData
    }

    fun getRestaurantsByLocation(location: String, cat: String, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetroInstancec().create(RetroService::class.java)
            val response = retroInstance.getByLocation(location, cat, limit)
            searchResultLiveData.postValue(response)
        }
    }

    fun getRestaurantsByTerm(term: String, location: String, cat: String, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetroInstancec().create(RetroService::class.java)
            val response = retroInstance.getByTerm(term, location, cat, limit)
            searchResultLiveData.postValue(response)
        }
    }
}