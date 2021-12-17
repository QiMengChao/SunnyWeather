package com.qmc.sunnyweather.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.qmc.sunnyweather.R
import com.tencent.mmkv.MMKV
import java.lang.reflect.ParameterizedType

/**
 * @ProjectName:SunnyWeather
 * @Description:
 * @Author:qimengchao
 * @Date:2021/12/16 14:59
 */
abstract class BaseActivity<VM:ViewModel,VB:ViewBinding>:AppCompatActivity() {
    lateinit var vm:VM
    lateinit var vb:VB
    protected val kv by lazy {
        MMKV.defaultMMKV()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProvider(this)[clazz1]
        val clazz2 = type.actualTypeArguments[1] as Class<VB>
        val method = clazz2.getMethod("inflate",LayoutInflater::class.java)
        vb = method.invoke(clazz2,layoutInflater) as VB
        setContentView(vb.root)
        initView()
        initData()
        initStatusBar()
    }

    protected fun initStatusBar(@ColorRes color:Int = R.color.transparent) {
        immersionBar{
          transparentStatusBar()
        }
    }

    abstract fun initData()

    abstract fun initView()

}