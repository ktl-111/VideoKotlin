package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.searchmodel.R
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import l.liubin.com.videokotlin.utils.GlideUils
import l.liubin.com.videokotlin.utils.durationFormat

/**
 * Created by l on 2018/5/10.
 */
class HotViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<HomeBean.Issue.Item>(parent, R.layout.itemview_hot) {
    var iv_img: ImageView
    var tv_content: TextView
    var mContext = context

    init {
        iv_img = `$`(R.id.iv_itemview_hot_img)
        tv_content = `$`(R.id.tv_itemview_hot_content)
    }

    override fun setData(data: HomeBean.Issue.Item) {
        GlideUils.loadImg(mContext, data.data?.cover?.feed!!, iv_img)
        var content = SpannableString("${data.data!!.title}\n#${data.data!!.category}/${durationFormat(data.data!!.duration)}")
        //new RelativeSizeSpan(0.5f), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        content.setSpan(RelativeSizeSpan(0.8f), data.data!!.title.length, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_content.text = content
    }
}