package com.qmc.sunnyweather.logic.model

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 14:27
 */
data class Weather(val realtime:RealTimeResponse.Realtime,val daily:DailyResponse.Daily)