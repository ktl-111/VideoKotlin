package com.example.downloadmodel.mvp

import com.example.downloadmodel.api.DownloadApi
import l.liubin.com.videokotlin.mvp.base.BaseModel


/**
 * Created by steam_lb on 2018/9/1/001.
 */
class DownloadMvpModel : BaseModel<DownloadApi>() {
    override fun getClazz(): Class<DownloadApi> = apiClazz

    companion object {
        val apiClazz = DownloadApi::class.java
    }
}