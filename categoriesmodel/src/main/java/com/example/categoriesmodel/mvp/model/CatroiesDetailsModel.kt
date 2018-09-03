package com.example.categoriesmodel.mvp.model

import com.example.categoriesmodel.mvp.CatrgoriesBaseModel
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/11.
 */
class CatroiesDetailsModel : CatrgoriesBaseModel() {
    fun getDetailsList(id: Long, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getCategoryDetailList(id), baseObserver)
    }

    fun getMore(url: String, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getIssueData(url), baseObserver)
    }

}