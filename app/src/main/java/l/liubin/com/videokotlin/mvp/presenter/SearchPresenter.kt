package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.SearchModel
import l.liubin.com.videokotlin.mvp.view.SearchView

/**
 * Created by l on 2018/5/14.
 */
class SearchPresenter(mView: SearchView) : BasePresenter<SearchView, SearchModel>(mView) {
    var nextUrl: String? = null
    fun getHotSearch() {
        mModel.getHotSearch(object : BaseObserver<ArrayList<String>>(this@SearchPresenter, mView!!) {
            override fun onSuccess(data: ArrayList<String>) {
                mView?.apply { showHotSearch(data) }
            }
        })
    }

    fun getSearchData(content: String) {
        mModel.getSearchData(content, object : BaseObserver<HomeBean.Issue>(this@SearchPresenter, mView!!) {
            override fun onSuccess(data: HomeBean.Issue) {
                nextUrl = data.nextPageUrl
                mView?.apply { showSearchData(data.itemList) }
            }
        })
    }

    fun getMore() {
        nextUrl?.also {
            mModel.getMoreList(it, object : BaseObserver<HomeBean.Issue>(this@SearchPresenter, mView!!) {
                override fun onSuccess(data: HomeBean.Issue) {
                    mView?.apply {
                        nextUrl = data.nextPageUrl
                        showSearchData(data.itemList)
                    }
                }
            })
        }

    }

    override fun createModel(): SearchModel = SearchModel()
}