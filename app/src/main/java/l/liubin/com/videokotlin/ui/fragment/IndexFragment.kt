package l.liubin.com.videokotlin.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import com.gyf.barlibrary.ImmersionBar
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.fragment_index.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpFragment
import l.liubin.com.videokotlin.mvp.presenter.IndexPresenter
import l.liubin.com.videokotlin.mvp.view.IndexView
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.utils.SingToast
import l.liubin.com.videokotlin.utils.Utils
import l.liubin.com.videokotlin.viewholder.BannerViewHolder
import l.liubin.com.videokotlin.viewholder.IndexTitleViewHolder
import l.liubin.com.videokotlin.viewholder.IndexViewHolder

/**
 * Created by l on 2018/5/9.
 */
class IndexFragment : MvpFragment<IndexPresenter>(), IndexView, SwipeRefreshLayout.OnRefreshListener {
    companion object {
        val TITLE: String = "textHeader"
        val Banner: String = "banner"
        val NORMAL: String = "video"
    }

    override fun getResId(): Int = R.layout.fragment_index
    lateinit var mAdapter: RecyclerArrayAdapter<HomeBean.Issue.Item>

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImmersionBar.setTitleBar(mContext as BaseActivity, tb_index_bar)
    }

    override fun initData() {
        mAdapter = object : RecyclerArrayAdapter<HomeBean.Issue.Item>(mContext) {
            val TYPE_BANNER: Int = 1.shl(1)
            val TYPE_NORMAL: Int = 1.shl(2)
            val TYPE_TITLE: Int = 1.shl(3)

            override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<HomeBean.Issue.Item> {
                return when (viewType) {
                    TYPE_TITLE -> IndexTitleViewHolder(parent!!, mContext)
                    TYPE_BANNER -> BannerViewHolder(parent!!, mContext)
                    else -> IndexViewHolder(parent!!, mContext)
                }
            }

            override fun getViewType(position: Int): Int {
                var item = mAdapter.getItem(position)
                return when (item.tag) {
                    TITLE -> TYPE_TITLE
                    Banner -> TYPE_BANNER
                    else -> TYPE_NORMAL
                }
            }
        }
        tb_index_bar.setBackgroundColor(Utils.getColor(R.color.transparent))
        onRefresh()
    }

    override fun initEvent() {
        erv_index_list.setRefreshListener(this)
    }

    override fun onRefresh() {
        mPresenter.getIndexList(1)
    }

    override fun onSuccess(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun onError(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun showLoading() {
        erv_index_list.swipeToRefresh.isRefreshing = true
    }

    override fun hindeLoading() {
        erv_index_list.swipeToRefresh.isRefreshing = false
    }

    override fun showBannerList(itemList: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun showMoreList(itemList: ArrayList<HomeBean.Issue.Item>) {
    }

    override fun createPresent(): IndexPresenter {
        return IndexPresenter(this)
    }
}