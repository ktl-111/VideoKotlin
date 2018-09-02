package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.example.indexmodel.R
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder

/**
 * Created by l on 2018/5/9.
 */
class IndexTitleViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<HomeBean.Issue.Item>(parent, R.layout.itemview_indextitle) {
    lateinit var tv_title: TextView

    init {
        tv_title = `$`<TextView>(R.id.tv_itemview_index_title)
    }

    var mContext: Context = context
    override fun setData(data: HomeBean.Issue.Item) {
        tv_title.text = data.data?.text
    }
}