package com.example.downloadmodel.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by steam_lb on 2018/9/1/001.
 */
interface DownloadApi {
    @Streaming
    @GET
    fun download(@Header("Range") range: String, @Url url: String): Observable<Response<ResponseBody>>

    @Streaming
    @GET
    fun check(@Url url: String): Observable<Response<ResponseBody>>
}