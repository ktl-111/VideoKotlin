package com.example.popularmodel.mvp.persenter

import com.example.popularmodel.mvp.model.PopularModel
import com.example.popularmodel.mvp.view.PopularView
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter

/**
 * Created by l on 2018/5/10.
 */
class PopularPresenter(mView: PopularView) : BasePresenter<PopularView, PopularModel>(mView) {
    fun getPopular() {
        mModel.getPopular(object : BaseObserver<TabInfoBean>(this@PopularPresenter, mView!!) {
            override fun onSuccess(data: TabInfoBean) {
                mView?.showPopular(data)
            }
        })
    }

    override fun createModel(): PopularModel = PopularModel()
}