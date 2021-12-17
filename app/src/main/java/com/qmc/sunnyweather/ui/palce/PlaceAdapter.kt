package com.qmc.sunnyweather.ui.palce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qmc.sunnyweather.databinding.PlaceItemBinding
import com.qmc.sunnyweather.logic.model.OnAdapterItemClickListener
import com.qmc.sunnyweather.logic.model.Place

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 15:00
 */
class PlaceAdapter(private val places: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private var onAdapterItemClickListener: OnAdapterItemClickListener<Place>?=null

    inner class PlaceViewHolder(placeItemBinding: PlaceItemBinding) :
        RecyclerView.ViewHolder(placeItemBinding.root) {
        val placeName = placeItemBinding.placeName
        val placeAddress = placeItemBinding.placeAddress
        val placeCv = placeItemBinding.placeCv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            PlaceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setOnItemClickListener(onAdapterItemClickListener: OnAdapterItemClickListener<Place>) {
        this.onAdapterItemClickListener = onAdapterItemClickListener
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.placeName.text = places[position].name
        holder.placeAddress.text = places[position].address
        holder.placeCv.setOnClickListener {
            onAdapterItemClickListener?.setOnItemClickListener(position,it,places[position])
        }
    }

    override fun getItemCount() = places.size
}