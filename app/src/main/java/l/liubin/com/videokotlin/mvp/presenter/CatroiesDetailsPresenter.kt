package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.CatroiesDetailsModel
import l.liubin.com.videokotlin.mvp.view.CatroriesDetailsView

/**
 * Created by l on 2018/5/11.
 */
class CatroiesDetailsPresenter(mView: CatroriesDetailsView) : BasePresenter<CatroriesDetailsView, CatroiesDetailsModel>(mView) {
    lateinit var nextUrl: String
    fun getDetailsList(id: Long) {
        mModel.getDetailsList(id, object : BaseObserver<HomeBean.Issue>(this@CatroiesDetailsPresenter, mView!!) {
            override fun onSuccess(data: HomeBean.Issue) {
                nextUrl = data.nextPageUrl
                mView?.showDetailsList(data.itemList)
            }
        })
    }

    fun getMore() {
        mModel.getMore(nextUrl, object : BaseObserver<HomeBean.Issue>(this@CatroiesDetailsPresenter, mView!!) {
            override fun onSuccess(data: HomeBean.Issue) {
                mView?.addMore(data.itemList)
            }
        })
    }

    override fun createModel(): CatroiesDetailsModel = CatroiesDetailsModel()
}