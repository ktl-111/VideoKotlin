package l.liubin.com.videokotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.fragment.IndexFragment
import l.liubin.com.videokotlin.utils.GlideImageLoader

/**
 * Created by l on 2018/5/9.
 */
class BannerViewHolder(parent: ViewGroup, context: Context, indexFragment: IndexFragment) : BaseViewHolder<HomeBean.Issue>(parent, R.layout.itemview_banner), IndexFragment.FragmentStateListener {
    var mContext: Context = context
    var banner: Banner

    init {
        banner = `$`(R.id.banner_itemview_index)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.setImageLoader(GlideImageLoader())
        indexFragment.setFragmentStateListener(this)
    }

    override fun setData(data: HomeBean.Issue) {
        var titles: ArrayList<String> = arrayListOf()
        var imgs: ArrayList<String> = arrayListOf()
        data.itemList.forEach {
            titles.add(it.data?.title!!)
            imgs.add(it.data?.cover.feed)
        }
        banner.setImages(imgs)
        banner.setBannerTitles(titles)
        banner.setOnBannerListener { position ->
            var item = data.itemList.get(position)
            IndexFragment.setBannerClickListener(mContext, item)
        }
        banner.start()
    }

    override fun onResume() {
        banner?.let { it.start() }
    }

    override fun onPause() {
        banner?.let { it.stopAutoPlay() }
    }
}