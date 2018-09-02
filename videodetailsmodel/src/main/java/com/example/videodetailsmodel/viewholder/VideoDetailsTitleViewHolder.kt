package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.example.videodetailsmodel.R
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder

/**
 * Created by steam_lb on 2018/5/15/015.
 */
class VideoDetailsTitleViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<HomeBean.Issue.Item>(parent, R.layout.itemview_videodetailscontent_title) {
    var tv_title: TextView

    init {
        tv_title = `$`(R.id.tv_itemview_videodetailstitle_title)
    }

    override fun setData(data: HomeBean.Issue.Item) {
        tv_title.text = data.data?.text
    }
}