package l.liubin.com.videokotlin.mvp.view

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseView

/**
 * Created by l on 2018/5/9.
 */
interface IndexView :BaseView {
    fun showBannerList(itemList:ArrayList<HomeBean.Issue.Item>)
    fun showMoreList(itemList:ArrayList<HomeBean.Issue.Item>)
}