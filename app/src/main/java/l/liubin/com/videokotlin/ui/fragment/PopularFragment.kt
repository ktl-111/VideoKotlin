package l.liubin.com.videokotlin.ui.fragment

import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpFragment
import l.liubin.com.videokotlin.mvp.presenter.PopularPresenter
import l.liubin.com.videokotlin.mvp.view.PopularView
import l.liubin.com.videokotlin.utils.SingToast

/**
 * Created by l on 2018/5/9.
 */
class PopularFragment : MvpFragment<PopularPresenter>(), PopularView {
    override fun onSuccess(msg: String) {
        SingToast.showToast(mContext,msg)
    }

    override fun onError(msg: String) {
        SingToast.showToast(mContext,msg)
    }

    override fun showLoading() {
    }

    override fun hindeLoading() {
    }

    override fun showPopular(bean: TabInfoBean) {

    }

    override fun createPresent(): PopularPresenter = PopularPresenter(this)

    override fun getResId(): Int = R.layout.fragment_popular

    override fun initData() {
    }

    override fun initEvent() {
    }
}