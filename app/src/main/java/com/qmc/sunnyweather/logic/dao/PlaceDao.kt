package com.qmc.sunnyweather.logic.dao

import com.google.gson.Gson
import com.qmc.sunnyweather.logic.model.Place
import com.tencent.mmkv.MMKV

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 18:38
 */
object PlaceDao {
    private val kv by lazy {
        MMKV.defaultMMKV()
    }
    fun savePlace(place: Place) {
        kv.encode("place",Gson().toJson(place))
    }
    fun getSavePlace():Place {
      return Gson().fromJson(kv.getString("place",""),Place::class.java)
    }
    fun  isPlaceSave() = kv.containsKey("place")
}