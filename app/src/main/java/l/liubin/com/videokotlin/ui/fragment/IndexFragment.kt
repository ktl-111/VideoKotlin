package l.liubin.com.videokotlin.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import l.liubin.com.videokotlin.utils.*
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

        fun setBannerClickListener(context: Context, homeBean: HomeBean.Issue.Item) {

        }
    }

    var toolbarHeight: Double = 0.0
    var dty: Double = 0.0
    var erv_scrolly: Double = 0.0
    override fun getResId(): Int = R.layout.fragment_index
    lateinit var mAdapter: RecyclerArrayAdapter<Any>
    var isShowTitle: Boolean = false//是否显示title


    override fun initData() {
        ImmersionBar.setTitleBar(mContext as BaseActivity, tb_index_bar)
        mAdapter = object : RecyclerArrayAdapter<Any>(mContext) {
            val TYPE_BANNER: Int = 1.shl(1)
            val TYPE_NORMAL: Int = 1.shl(2)
            val TYPE_TITLE: Int = 1.shl(3)

            override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
                return when (viewType) {
                    TYPE_TITLE -> IndexTitleViewHolder(parent!!, mContext)
                    TYPE_BANNER -> BannerViewHolder(parent!!, mContext, this@IndexFragment)
                    else -> IndexViewHolder(parent!!, mContext)
                }
            }

            override fun getViewType(position: Int): Int {
                var item = mAdapter.getItem(position)
                return when (item) {
                    is HomeBean.Issue -> TYPE_BANNER
                    else -> when ((item as HomeBean.Issue.Item).type) {
                        TITLE -> TYPE_TITLE
                        else -> TYPE_NORMAL
                    }
                }
            }
        }
        erv_index_list.setLayoutManager(LinearLayoutManager(mContext))
        erv_index_list.adapter = mAdapter
        tb_index_bar.setBackgroundColor(Utils.getColor(R.color.transparent))
        tb_index_bar.post {
            toolbarHeight = tb_index_bar.measuredHeight.toDouble()
            dty = dip2px(mContext, 200.0f + 44.0f) - toolbarHeight!!
        }
        initAdapter(mAdapter, object : AdapterListener() {
            override fun onMoreShow() {
                getMore()
            }

            override fun onErrorClick() {
                mAdapter.resumeMore()
            }
        })
        onRefresh()
    }

    var listener: FragmentStateListener? = null

    interface FragmentStateListener {
        fun onResume()
        fun onPause()
    }

    fun setFragmentStateListener(listener: FragmentStateListener) {
        this.listener = listener
    }

    override fun onResume() {
        super.onResume()
        listener?.let { it.onResume() }
    }

    override fun onPause() {
        super.onPause()
        listener?.let { it.onPause() }
    }

    override fun initEvent() {
        erv_index_list.setRefreshListener(this)
        erv_index_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                (1..10)
                        .takeWhile { it >= 4 }
                        .forEach { println(it) }
                if (mAdapter.allData.size > 0) {
                    var manager = recyclerView.layoutManager as LinearLayoutManager
                    var postition = manager.findFirstCompletelyVisibleItemPosition()
                    if (postition >= 0) {
                        var item = mAdapter.getItem(postition)
                        if (item is HomeBean.Issue.Item) {
                            var view = manager.findViewByPosition(postition)
                            var loca: IntArray = kotlin.IntArray(2)
                            view.getLocationOnScreen(loca)
                            if (isShowTitle) {
                                println("${loca[1]}  ${loca[1] + view.measuredHeight}  ${toolbarHeight}")
                                if ((loca[1] + view.measuredHeight) <= toolbarHeight) {
                                    tv_index_title.text = item.data?.text
                                } else {
                                    var start = false
                                    for (it in mAdapter.allData.reversed()) {
                                        if (it == item) {
                                            start = true
                                        }
                                        if (start && it is HomeBean.Issue.Item && it.type == "textHeader" && it != item) {
                                            tv_index_title.text = it.data?.text
                                            break
                                        }
                                    }
                                }
                            } else {
                                tv_index_title.text = ""
                            }
                        }
                    }
                }
                erv_scrolly += dy
                if (erv_scrolly <= 0) {
                    tb_index_bar.setBackgroundColor(Color.parseColor("#00000000"))
                    return
                }
                var number: Double = 1.0 - (dty - erv_scrolly) / dty
                if (number > 1) {
                    isShowTitle = true
                    tb_index_bar.setBackgroundColor(Color.parseColor("#ffffffff"))
                    return
                }
                isShowTitle = false
                var alpha = Math.round(number * 255)
                var hex = Integer.toHexString(alpha.toInt()).toUpperCase()
                if (hex.length == 1) {
                    hex = "0$hex"
                }
                var searchImg: Bitmap
                try {
                    searchImg = if (Integer.parseInt(hex, 16) > Integer.parseInt("B3", 16)) {
                        BitmapFactory.decodeResource(mContext.resources, R.mipmap.ic_action_search_black)
                    } else {
                        BitmapFactory.decodeResource(mContext.resources, R.mipmap.ic_action_search_white)
                    }
                    iv_index_search.setImageBitmap(searchImg)
                } catch (e: Exception) {
                }
                hex = "#${hex}FFFFFF"
                tb_index_bar.setBackgroundColor(Color.parseColor(hex))
            }
        })
    }

    override fun onRefresh() {
        mPresenter.getIndexList(1)
    }

    fun getMore() {
        mPresenter.getMoreList()
    }

    override fun onSuccess(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun onError(msg: String) {
        SingToast.showToast(mContext, msg)
        if (isMore) {
            mAdapter.pauseMore()
        }
    }

    override fun showLoading() {
        if (!isMore) {
            erv_index_list.swipeToRefresh.isRefreshing = true
        }
    }

    override fun hindeLoading() {
        if (!isMore) {
            erv_index_list.swipeToRefresh.isRefreshing = false
        }
    }

    override fun showBannerList(item: HomeBean.Issue) {
        isMore = false
        mAdapter.clear()
        mAdapter.add(item)
    }

    var isMore: Boolean = false
    override fun showMoreList(itemList: ArrayList<HomeBean.Issue.Item>) {
        isMore = true
        mAdapter.addAll(itemList)
        if (itemList.size < 3) {
            mAdapter.stopMore()
        }
    }

    override fun createPresent(): IndexPresenter {
        return IndexPresenter(this)
    }
}