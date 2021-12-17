package com.qmc.sunnyweather.ui.palce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.qmc.sunnyweather.logic.Repository
import com.qmc.sunnyweather.logic.model.Place

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 14:10
 */
class PlaceViewModel:ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(it)
    }

    fun searchPlaces(query:String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getPlace() = Repository.getPlace()

    fun isPlaceSave() = Repository.isPlaceSave()

}