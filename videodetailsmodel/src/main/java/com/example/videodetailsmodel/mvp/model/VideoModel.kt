package com.example.videodetailsmodel.mvp.model

import com.example.videodetailsmodel.mvp.VideoDetailsModel
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by l on 2018/5/15.
 */
class VideoModel : VideoDetailsModel() {
    fun getOrtherData(id: Long, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getRelatedData(id), baseObserver)
    }

    fun checkUrl(url: String, baseObserver: BaseObserver<Response<ResponseBody>>) {
        universal(mApiService.check(url), baseObserver)
    }
}