package com.example.videodetailsmodel.mvp.presenter

import com.example.videodetailsmodel.mvp.model.VideoModel
import com.example.videodetailsmodel.mvp.view.VideoView
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpHeaders
import retrofit2.Response

/**
 * Created by l on 2018/5/15.
 */
class VideoPresenter(mView: VideoView) : BasePresenter<VideoView, VideoModel>(mView) {
    fun getOrtherData(id: Long) {
        mModel.getOrtherData(id, object : BaseObserver<HomeBean.Issue>(this@VideoPresenter, mView!!) {
            override fun onSuccess(data: HomeBean.Issue) {
                mView?.apply { showOrtherView(data.itemList) }
            }
        })
    }

    fun checkUrl(url: String) {
        mModel.checkUrl(url, object : BaseObserver<Response<ResponseBody>>(this@VideoPresenter, mView!!) {
            override fun onSuccess(data: Response<ResponseBody>) {
                mView?.apply {
                    showUrlSize(HttpHeaders.contentLength(data.headers()))
                }
            }
        })
    }

    override fun createModel(): VideoModel = VideoModel()
}