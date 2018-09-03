package com.example.popularmodel.mvp.model

import com.example.popularmodel.mvp.PopularBaseModel
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import l.liubin.com.videokotlin.mvp.base.BaseModel.Companion.universal
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/10.
 */
class PopularModel: PopularBaseModel() {
    fun getPopular(observer: BaseObserver<TabInfoBean>){
        universal(mApiService.getRankList(),observer)
    }
}