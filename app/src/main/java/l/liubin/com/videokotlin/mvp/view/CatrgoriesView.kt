package l.liubin.com.videokotlin.mvp.view

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import l.liubin.com.videokotlin.mvp.base.BaseView

/**
 * Created by l on 2018/5/10.
 */
interface CatrgoriesView:BaseView {
    fun showList(list:ArrayList<CategoryBean>)
}