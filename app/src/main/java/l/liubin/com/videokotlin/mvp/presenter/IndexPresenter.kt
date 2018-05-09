package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.IndexModel
import l.liubin.com.videokotlin.mvp.view.IndexView

/**
 * Created by l on 2018/5/9.
 */
class IndexPresenter(mView: IndexView) : BasePresenter<IndexView, IndexModel>(mView) {
    fun getIndexList(num: Int) {
        var observer = object : BaseObserver<HomeBean>(this@IndexPresenter, mvpView!!) {
            override fun onSuccess(data: HomeBean) {
            }
        }
        mModel.getIndexList(num, observer)
    }

    override fun createModel(): IndexModel = IndexModel()
}