package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.utils.GlideUils

/**
 * Created by l on 2018/5/10.
 */
class CatrgoriesViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<CategoryBean>(parent, R.layout.itemview_categories) {
    var mContext = context
    var iv_img: ImageView
    var tv_tag: TextView
    var tv_content: TextView

    init {
        iv_img = `$`(R.id.iv_itemview_categories_img)
        tv_tag = `$`(R.id.tv_itemview_catrgories_tag)
        tv_content = `$`(R.id.tv_itemview_catrgories_content)
    }

    override fun setData(data: CategoryBean) {
        var params = itemView.layoutParams
        params.height = data.imgHeight
        itemView.layoutParams = params

        GlideUils.loadImg(mContext, data.bgPicture, iv_img)
        tv_tag.text = "#${data.name}"
        tv_content.text = data.description
    }
}