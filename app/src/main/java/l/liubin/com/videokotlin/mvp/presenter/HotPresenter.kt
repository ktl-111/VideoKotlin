package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.HotModel
import l.liubin.com.videokotlin.mvp.view.HotView

/**
 * Created by l on 2018/5/10.
 */
class HotPresenter(mView: HotView) : BasePresenter<HotView, HotModel>(mView) {
    fun getList(url: String) {
        mModel.getList(url, object : BaseObserver<HomeBean.Issue>(this@HotPresenter, mView!!) {
            override fun onSuccess(data: HomeBean.Issue) {
                mView?.showList(data.itemList)
            }
        })
    }

    override fun createModel(): HotModel = HotModel()
}