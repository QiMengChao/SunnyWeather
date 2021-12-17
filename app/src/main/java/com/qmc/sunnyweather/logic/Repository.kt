package com.qmc.sunnyweather.logic

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import com.qmc.sunnyweather.logic.dao.PlaceDao
import com.qmc.sunnyweather.logic.model.Place
import com.qmc.sunnyweather.logic.model.Weather
import com.qmc.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 13:48
 */
object Repository {

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getPlace() = PlaceDao.getSavePlace()

    fun isPlaceSave() = PlaceDao.isPlaceSave()

    fun searchPlaces(query:String) = fire(Dispatchers.IO){
        val searchPlaces = SunnyWeatherNetwork.searchPlaces(query)
        Log.e(TAG, "searchPlaces: ${searchPlaces.places}")
        if(searchPlaces.status == "ok") {
            Result.success(searchPlaces.places)
        } else {
            Result.failure(RuntimeException("response status is ${searchPlaces.status}"))
        }
    }


    fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferRealtime.await()
            val dailyResponse = deferredDaily.await()
            if(realtimeResponse.status == "ok"&&dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"+"daily response status is ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context:CoroutineContext,block:suspend () -> Result<T>) = liveData(context) {
        val result = try {
            block()
        } catch (e:Exception) {
            Result.failure(e)
        }
        emit(result)
    }

}