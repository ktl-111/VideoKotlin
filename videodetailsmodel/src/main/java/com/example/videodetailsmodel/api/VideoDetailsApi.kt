package com.example.videodetailsmodel.api

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by steam_lb on 2018/9/2/002.
 */
interface VideoDetailsApi {
    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>
    @Streaming
    @GET
    fun check(@Url url: String): Observable<Response<ResponseBody>>
}