package com.qmc.sunnyweather

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 10:53
 */
class SunnyWeatherApplication:Application() {

    companion object {
        const val TOKEN = "LedaUoBVXA3MYLky"
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        MMKV.initialize(this)
    }

}