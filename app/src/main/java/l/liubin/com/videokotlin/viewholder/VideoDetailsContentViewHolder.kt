package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.ViewGroup
import android.widget.TextView
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.utils.GlideUils
import l.liubin.com.videokotlin.utils.durationFormat

/**
 * Created by steam_lb on 2018/5/15/015.
 */
class VideoDetailsContentViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<HomeBean.Issue.Item>(parent, R.layout.itemview_videodetailscontent) {
    var riv_img: RoundedImageView
    var tv_content: TextView
    var mContext = context

    init {
        riv_img = `$`(R.id.riv_itemview_videodetailscontent_img)
        tv_content = `$`(R.id.tv_itemview_videodetailscontent_text)
    }

    override fun setData(data: HomeBean.Issue.Item) {
        GlideUils.loadImg(mContext, data.data?.cover?.feed!!, riv_img)
        var content = SpannableString("${data.data.title}\n#${data.data.category}/${durationFormat(data.data.duration)}")
        content.setSpan(RelativeSizeSpan(0.7f), data.data.title.length, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        content.setSpan(ForegroundColorSpan(Color.parseColor("#aaaaaa")), data.data.title.length, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_content.text = content
    }
}