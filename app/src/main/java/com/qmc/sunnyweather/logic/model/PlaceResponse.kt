package com.qmc.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 10:58
 */
data class PlaceResponse(val status:String,val places:List<Place>)

data class Place(val name:String,val location:Location,@SerializedName("formatted_address") val address:String)

data class Location(val lng:String,val lat:String)