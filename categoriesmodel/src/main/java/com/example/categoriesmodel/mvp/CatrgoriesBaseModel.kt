package com.example.categoriesmodel.mvp

import com.example.categoriesmodel.api.CatrgoriesApi
import l.liubin.com.videokotlin.mvp.base.BaseModel

/**
 * Created by steam_l on 2018/9/3.
 * Desprition :
 */
open class CatrgoriesBaseModel : BaseModel<CatrgoriesApi>() {
    override fun getClazz(): Class<CatrgoriesApi> = apiClazz

    companion object {
        val apiClazz = CatrgoriesApi::class.java
    }
}