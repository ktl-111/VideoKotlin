package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import l.liubin.com.videokotlin.R

/**
 * Created by l on 2018/5/9.
 */
class BannerViewHolder(parent: ViewGroup, context: Context):BaseViewHolder<HomeBean.Issue.Item>(parent, R.layout.itemview_banner) {
    var mContext: Context = context
    override fun setData(data: HomeBean.Issue.Item?) {
    }
}