package com.example.searchmodel.mvp.model

import com.example.searchmodel.mvp.SearchBaseModel
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/14.
 */
class SearchModel : SearchBaseModel()  {
    fun getHotSearch(baseObserver: BaseObserver<ArrayList<String>>) {
        universal(mApiService.getHotWord(), baseObserver)
    }

    fun getSearchData(content: String, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getSearchData(content), baseObserver)
    }

    fun getMoreList(url: String, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getIssueData(url), baseObserver)
    }

}