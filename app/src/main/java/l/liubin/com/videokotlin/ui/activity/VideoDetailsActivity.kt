package l.liubin.com.videokotlin.ui.activity

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpActivity
import l.liubin.com.videokotlin.mvp.presenter.VideoPresenter
import l.liubin.com.videokotlin.mvp.view.VideoView
import l.liubin.com.videokotlin.utils.SingToast

/**
 * Created by l on 2018/5/15.
 */
class VideoDetailsActivity:MvpActivity<VideoPresenter>(), VideoView {
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

    override fun showOrtherView(list: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun createPresenter(): VideoPresenter  = VideoPresenter(this)

    override fun getResId(): Int = R.layout.activity_videodetails

    override fun initData() {
    }

    override fun initEvent() {
    }
}