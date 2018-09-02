package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.example.searchmodel.R
import com.jude.easyrecyclerview.adapter.BaseViewHolder

/**
 * Created by l on 2018/5/14.
 */
class HotSearchViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<String>(parent, R.layout.itemview_hotsearch) {
    var tv_toast: TextView

    init {
        tv_toast = `$`(R.id.tv_itemview_search_toast)
    }

    override fun setData(data: String) {
        tv_toast.text = data
    }
}