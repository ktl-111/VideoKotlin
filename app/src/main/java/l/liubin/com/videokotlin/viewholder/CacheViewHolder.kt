package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.datebase.DownloadModel

/**
 * Created by l on 2018/5/21.
 */
class CacheViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<DownloadModel>(parent, R.layout.itemview_cache) {
    var mContext = context
    override fun setData(data: DownloadModel?) {
        
    }
}