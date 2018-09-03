package com.example.popularmodel.mvp

import com.example.popularmodel.api.PopularApi
import l.liubin.com.videokotlin.mvp.base.BaseModel

/**
 * Created by steam_l on 2018/9/3.
 * Desprition :
 */
open class PopularBaseModel : BaseModel<PopularApi>() {
    override fun getClazz(): Class<PopularApi> = apiClazz

    companion object {
        val apiClazz = PopularApi::class.java
    }
}