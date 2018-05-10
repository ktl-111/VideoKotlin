package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.utils.GlideUils
import l.liubin.com.videokotlin.utils.durationFormat

/**
 * Created by l on 2018/5/9.
 */
class IndexViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<HomeBean.Issue.Item>(parent, R.layout.itemview_index) {
    private var iv_avatar: RoundedImageView
    private var iv_img: ImageView
    private var tv_category: TextView
    private var tv_title: TextView
    private var tv_tag: TextView
    var mContext: Context = context

    init {
        tv_title = `$`(R.id.tv_itemview_index_normal_title)
        tv_tag = `$`(R.id.tv_itemview_index_normal_tag)
        tv_category = `$`(R.id.tv_itemview_index_normal_category)
        iv_img = `$`(R.id.iv_itemview_index_normal_img)
        iv_avatar = `$`(R.id.iv_itemview_index_normal_avatar)
    }

    override fun setData(data: HomeBean.Issue.Item) {
        loadImg(data.data?.provider?.icon, iv_avatar)
        loadImg(data.data?.cover?.feed, iv_img)
        tv_title.text = data.data?.title
        var duration = ""
        data.data?.tags?.forEach { it.name?.let { duration += it } }
        duration += durationFormat(data.data?.duration)
        tv_tag.text = duration
        tv_category.text = "#${data.data?.category}"
    }

    fun loadImg(url: String?, img: ImageView) {
        url?.let { GlideUils.loadImg(mContext, it, img) }
                ?: R.mipmap.pgc_default_avatar
                ?.let { GlideUils.loadImg(mContext, it, img) }
    }
}