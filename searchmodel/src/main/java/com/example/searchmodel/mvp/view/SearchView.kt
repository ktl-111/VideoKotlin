package com.example.searchmodel.mvp.view

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseView

/**
 * Created by l on 2018/5/14.
 */
interface SearchView :BaseView{
    fun showHotSearch(list:ArrayList<String>)
    fun showSearchData(data:ArrayList<HomeBean.Issue.Item>?)
}