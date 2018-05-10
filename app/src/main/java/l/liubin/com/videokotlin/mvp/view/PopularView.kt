package l.liubin.com.videokotlin.mvp.view

import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import l.liubin.com.videokotlin.mvp.base.BaseView

/**
 * Created by l on 2018/5/10.
 */
interface PopularView:BaseView {
    fun showPopular(bean:TabInfoBean)
}