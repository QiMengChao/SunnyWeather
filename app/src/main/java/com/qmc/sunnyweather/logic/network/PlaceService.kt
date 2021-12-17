package com.qmc.sunnyweather.logic.network

import com.qmc.sunnyweather.SunnyWeatherApplication
import com.qmc.sunnyweather.logic.model.DailyResponse
import com.qmc.sunnyweather.logic.model.PlaceResponse
import com.qmc.sunnyweather.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 11:01
 */
interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>


}