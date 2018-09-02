package com.example.videodetailsmodel.mvp

import com.example.videodetailsmodel.api.VideoDetailsApi
import l.liubin.com.videokotlin.mvp.base.BaseModel

/**
 * Created by steam_lb on 2018/9/2/002.
 */
open class VideoDetailsModel : BaseModel<VideoDetailsApi>() {
    override fun getClazz(): Class<VideoDetailsApi> = apiClazz

    companion object {
        val apiClazz = VideoDetailsApi::class.java
    }
}