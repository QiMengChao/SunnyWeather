package com.qmc.sunnyweather.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qmc.sunnyweather.R
import com.qmc.sunnyweather.base.BaseActivity
import com.qmc.sunnyweather.databinding.ActivityWeatherBinding
import com.qmc.sunnyweather.logic.model.Weather
import com.qmc.sunnyweather.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 14:59
 */
class WeatherActivity : BaseActivity<WeatherViewModel, ActivityWeatherBinding>(),SwipeRefreshLayout.OnRefreshListener {
    override fun initData() {
        if (vm.locationLng.isEmpty()) {
            vm.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (vm.locationLat.isEmpty()) {
            vm.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if(vm.placeName.isEmpty()) {
            vm.placeName = intent.getStringExtra("place_name") ?: ""
        }
        vm.weatherLiveData.observe(this, {
            val weather = it.getOrNull()
            if(weather != null) {
                showWeatherInfo(weather)
            } else {
              Toast.makeText(this,"无法成功获取天气",Toast.LENGTH_SHORT).show()
              it.exceptionOrNull()?.printStackTrace()
            }
            vb.swipeRefresh.isRefreshing = false
        })
        vm.refreshWeather(vm.locationLng,vm.locationLat)
    }

    private fun showWeatherInfo(weather: Weather) {


        vb.now.placeName.text = vm.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now数据
        vb.now.apply {
            val currentTempText = "${realtime.temperature.toInt()}℃"
            currentTemp.text = currentTempText
            currentSky.text = getSky(realtime.skycon).info
            val currentPM25Text = "空气指数${realtime.airQuality.aqi.chn.toInt()}"
            currentAQI.text = currentPM25Text
            nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        }
        // 填充forecast.xml数据
        vb.forecast.apply {
            forecastLayout.removeAllViews()
            val days = daily.skycon.size
            for (i in 0 until days) {
                val skycon = daily.skycon[i]
                val temperature = daily.temperature[i]
                val view = LayoutInflater.from(this@WeatherActivity).inflate(R.layout.forecast_item,forecastLayout,false)
                val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
                val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
                val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
                val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateInfo.text = simpleDateFormat.format(skycon.date)
                val sky = getSky(skycon.value)
                skyIcon.setImageResource(sky.icon)
                skyInfo.text = sky.info
                val tempText = "${temperature.min.toInt()} ～ ${temperature.max.toInt()} ℃"
                temperatureInfo.text = tempText
                forecastLayout.addView(view)
            }
        }
        // 填充life_index.xml布局的数据
        vb.lifeIndex.apply {
            val liefIndex = daily.lifeIndex
            coldRiskTv.text = liefIndex.coldRisk[0].desc
            dressingTv.text = liefIndex.dressing[0].desc
            ultravioletTv.text = liefIndex.ultraviolet[0].desc
            carWashingTv.text = liefIndex.carWashing[0].desc

        }
    }

    override fun initView() {
        vb.swipeRefresh.setColorSchemeResources(R.color.teal_700)
        vb.swipeRefresh.setOnRefreshListener(this)
        vb.now.navBtn.setOnClickListener {
            vb.drawerLayout.openDrawer(GravityCompat.START)
        }
        vb.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(vb.drawerLayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }

    override fun onRefresh() {
        vm.refreshWeather(vm.locationLng,vm.locationLat)
        vb.swipeRefresh.isRefreshing = true
    }
}