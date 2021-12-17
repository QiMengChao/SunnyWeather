package com.qmc.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.qmc.sunnyweather.logic.Repository
import com.qmc.sunnyweather.logic.model.Location

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 14:54
 */
class WeatherViewModel:ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(it.lng,it.lat)
    }
    fun refreshWeather(lng:String,lat:String) {
        locationLiveData.value = Location(lng, lat)
    }
}