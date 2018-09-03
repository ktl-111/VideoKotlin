package com.example.popularmodel.mvp.model

import com.example.popularmodel.mvp.PopularBaseModel
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/10.
 */
class HotModel: PopularBaseModel()  {
    fun getList(url:String,baseObserver: BaseObserver<HomeBean.Issue>){
        universal(mApiService.getIssueData(url),baseObserver)
    }
}