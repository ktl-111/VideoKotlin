package l.liubin.com.videokotlin.mvp.view

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseView

/**
 * Created by l on 2018/5/11.
 */
interface CatroriesDetailsView : BaseView {
    fun showDetailsList(list: ArrayList<HomeBean.Issue.Item>)
    fun addMore(list: ArrayList<HomeBean.Issue.Item>)
}