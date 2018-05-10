package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.PopularModel
import l.liubin.com.videokotlin.mvp.view.PopularView

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