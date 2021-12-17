package com.qmc.sunnyweather.logic.model

import android.view.View

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 17:27
 */
interface OnAdapterItemClickListener<T> {

    fun setOnItemClickListener(position:Int, view: View,t:T)

}