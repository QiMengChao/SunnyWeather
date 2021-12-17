package com.qmc.sunnyweather.ui.palce

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmc.sunnyweather.MainActivity
import com.qmc.sunnyweather.base.BaseFragment
import com.qmc.sunnyweather.databinding.FragmentPlcaeBinding
import com.qmc.sunnyweather.logic.model.OnAdapterItemClickListener
import com.qmc.sunnyweather.logic.model.Place
import com.qmc.sunnyweather.ui.weather.WeatherActivity

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 15:13
 */
class PlaceFragment : BaseFragment<PlaceViewModel, FragmentPlcaeBinding>(),OnAdapterItemClickListener<Place> {
    private lateinit var searchEt: EditText
    private lateinit var searchRc: RecyclerView
    private lateinit var placeAdapter: PlaceAdapter

    override fun initView() {
        searchEt = vb.searchEt
        searchRc = vb.searchRc
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        if(activity is MainActivity && vm.isPlaceSave()) {
            val place = vm.getPlace()
            val intent = Intent(
                context,
                WeatherActivity::class.java
            ).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        searchRc.layoutManager = LinearLayoutManager(activity)
        placeAdapter = PlaceAdapter(vm.placeList)
        searchRc.adapter = placeAdapter
        searchEt.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                vb.searchRc.visibility = View.VISIBLE
                vm.searchPlaces(content)
            } else {
                vb.searchRc.visibility = View.GONE
                vm.placeList.clear()
                placeAdapter.notifyDataSetChanged()
            }
        }
        vm.placeLiveData.observe(this, {
            val places = it.getOrNull()
            if (places != null) {
                vb.searchRc.visibility = View.VISIBLE
                vm.placeList.clear()
                vm.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "尚未查到任何地点", Toast.LENGTH_SHORT).show()
            }
        })
        placeAdapter.setOnItemClickListener(this)
    }

    override fun setOnItemClickListener(position: Int, view: View, t: Place) {
        if(activity is WeatherActivity) {
            val weatherActivity = activity as WeatherActivity
            weatherActivity.vb.drawerLayout.closeDrawers()
            weatherActivity.vm.locationLng = t.location.lng
            weatherActivity.vm.locationLat = t.location.lat
            weatherActivity.vm.placeName = t.name
            weatherActivity.onRefresh()
        } else {
            val intent = Intent(
                context,
                WeatherActivity::class.java
            ).apply {
                putExtra("location_lng",t.location.lng)
                putExtra("location_lat",t.location.lat)
                putExtra("place_name",t.name)
                vm.savePlace(t)
            }
            startActivity(intent)
            activity?.finish()
        }
    }
}