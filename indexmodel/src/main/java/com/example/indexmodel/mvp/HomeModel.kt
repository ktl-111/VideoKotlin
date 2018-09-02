package com.example.indexmodel.mvp

import com.example.indexmodel.api.IndexApi
import l.liubin.com.videokotlin.mvp.base.BaseModel

/**
 * Created by steam_lb on 2018/9/2/002.
 */
open class HomeModel : BaseModel<IndexApi>() {
    override fun getClazz(): Class<IndexApi> = apiClazz

    companion object {
        val apiClazz = IndexApi::class.java
    }
}