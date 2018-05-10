package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.IndexModel
import l.liubin.com.videokotlin.mvp.view.IndexView
import l.liubin.com.videokotlin.ui.fragment.IndexFragment
import l.liubin.com.videokotlin.utils.Utils

/**
 * Created by l on 2018/5/9.
 */
class IndexPresenter(mView: IndexView) : BasePresenter<IndexView, IndexModel>(mView) {
    lateinit var mBannerBean: HomeBean
    lateinit var nextUrl: String
    fun getIndexList(num: Int) {
        var function = Function<HomeBean, ObservableSource<HomeBean>> { homeBean ->
            nextUrl = homeBean.nextPageUrl
            var bannerList = homeBean.issueList[0].itemList.iterator()
            bannerList.forEach { item ->
                if (item.type == "banner2" || item.type == "horizontalScrollCard") {
                    bannerList.remove()
                } else {
                    item.tag = IndexFragment.Banner

                }
            }
            mBannerBean = homeBean
            mModel.getIndexMore(nextUrl)
        }
        var observer = object : BaseObserver<HomeBean>(this@IndexPresenter, mView!!) {
            override fun onSuccess(data: HomeBean) {
                mView?.showBannerList(mBannerBean.issueList[0])
                parseData(data)
            }
        }
        mModel.getIndexList(num, function, observer)
    }

    private fun parseData(data: HomeBean) {
        nextUrl = data.nextPageUrl
        data.issueList?.let {
            if (it.size > 0) {
                mView?.showMoreList(it[0].itemList)
            } else {
                mView?.onError(Utils.getStringFromResources(R.string.data_error))
            }
        } ?: mView?.onError(Utils.getStringFromResources(R.string.data_error))
    }

    fun getMoreList() {
        mModel.getIndexMore(nextUrl).subscribe(object : BaseObserver<HomeBean>(this@IndexPresenter, mView!!) {
            override fun onSuccess(data: HomeBean) {
                parseData(data)
            }
        })
    }

    override fun createModel(): IndexModel = IndexModel()
}