package com.qmc.sunnyweather.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.tencent.mmkv.MMKV
import java.lang.reflect.ParameterizedType

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/10 15:13
 */
abstract class BaseFragment<VM:ViewModel,VB:ViewBinding>: Fragment() {
    protected lateinit var vb:VB
    protected lateinit var vm: VM
    protected val kv by lazy {
        MMKV.defaultMMKV()
    }


    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          val type =  javaClass.genericSuperclass as ParameterizedType
          val clazz1 = type.actualTypeArguments[0] as Class<VM>
          vm = ViewModelProvider(this)[clazz1]
          val clazz2 = type.actualTypeArguments[1] as Class<VB>
          val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
          vb = method.invoke(clazz2,layoutInflater) as VB
          return vb.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()


    abstract fun initData()


}