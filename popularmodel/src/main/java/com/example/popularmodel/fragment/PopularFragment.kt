package l.liubin.com.videokotlin.ui.fragment

import android.content.Context
import com.example.base.MyApplication
import com.example.popularmodel.R
import com.example.popularmodel.mvp.persenter.PopularPresenter
import com.example.popularmodel.mvp.view.PopularView
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.include_title.*
import l.liubin.com.videokotlin.adapter.PopularAdapter
import l.liubin.com.videokotlin.mvp.base.MvpFragment
import l.liubin.com.videokotlin.utils.SingToast
import l.liubin.com.videokotlin.utils.Utils
import l.liubin.com.videokotlin.utils.dip2px
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * Created by l on 2018/5/9.
 */
class PopularFragment : MvpFragment<PopularPresenter>(), PopularView {
    override fun onSuccess(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun onError(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun showLoading() {
    }

    override fun hindeLoading() {
    }

    override fun showPopular(bean: TabInfoBean) {
        var urls = arrayListOf<String>()
        bean.tabInfo.tabList.forEach { urls.add(it.apiUrl) }
        vp_popular_content.adapter = PopularAdapter(fragmentManager!!, urls)
        vp_popular_content.offscreenPageLimit = urls.size
        var navigator = CommonNavigator(mContext)
        navigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context, postion: Int): IPagerTitleView {
                var titleView = ColorTransitionPagerTitleView(context)
                titleView.text = bean.tabInfo.tabList[postion].name
                titleView.normalColor = Utils.getColor(MyApplication.context, R.color.color_darker_gray)
                titleView.selectedColor = Utils.getColor(MyApplication.context,R.color.color_item_title)
                titleView.setOnClickListener { vp_popular_content.currentItem = postion }
                return titleView
            }

            override fun getCount(): Int = urls.size

            override fun getIndicator(context: Context): IPagerIndicator {
                var indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_MATCH_EDGE
                indicator.roundRadius = dip2px(context, 2f).toFloat()
                indicator.setColors(Utils.getColor(MyApplication.context,R.color.color_item_title))
                return indicator
            }
        }
        navigator.isAdjustMode = true
        mi_popular.navigator = navigator
        ViewPagerHelper.bind(mi_popular, vp_popular_content)
    }

    override fun createPresent(): PopularPresenter = PopularPresenter(this)

    override fun getResId(): Int = R.layout.fragment_popular

    override fun initData() {
        tv_include_title.text = Utils.getStringFromResources(MyApplication.context,R.string.popular)
        mPresenter.getPopular()
    }

    override fun initEvent() {
    }
}