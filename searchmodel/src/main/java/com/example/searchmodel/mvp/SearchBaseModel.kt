package com.example.searchmodel.mvp

import com.example.searchmodel.api.SearchApi
import l.liubin.com.videokotlin.mvp.base.BaseModel

/**
 * Created by steam_lb on 2018/9/2/002.
 */
open class SearchBaseModel : BaseModel<SearchApi>() {
    override fun getClazz(): Class<SearchApi> = apiClazz

    companion object {
        val apiClazz = SearchApi::class.java
    }
}