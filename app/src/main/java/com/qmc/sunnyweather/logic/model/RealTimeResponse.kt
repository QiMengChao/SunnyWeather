package com.qmc.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 14:15
 */
data class RealTimeResponse(val status:String,val result:Result) {
    data class Result(val realtime:Realtime)
    data class Realtime(val skycon:String,val temperature:Float,@SerializedName("air_quality") val airQuality:AirQuality)
    data class AirQuality(val aqi:AQI)
    data class AQI(val chn:Float)
}