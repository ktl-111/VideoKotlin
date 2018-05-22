package l.liubin.com.videokotlin.mvp.view

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseView

/**
 * Created by l on 2018/5/15.
 */
interface VideoView : BaseView {
    fun showOrtherView(list: ArrayList<HomeBean.Issue.Item>)
    fun showUrlSize(size:Long)
}