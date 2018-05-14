package l.liubin.com.videokotlin.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.activity_search.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpActivity
import l.liubin.com.videokotlin.mvp.presenter.SearchPresenter
import l.liubin.com.videokotlin.mvp.view.SearchView
import l.liubin.com.videokotlin.orther.FlowManager
import l.liubin.com.videokotlin.utils.*
import l.liubin.com.videokotlin.viewholder.HotSearchViewHolder
import l.liubin.com.videokotlin.viewholder.HotViewHolder

/**
 * Created by l on 2018/5/14.
 */
class SearchActivity : MvpActivity<SearchPresenter>(), SearchView {
    lateinit var mAdapter: RecyclerArrayAdapter<Any>
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

    override fun showHotSearch(list: ArrayList<String>) {
        erv_search_list.setLayoutManager(FlowManager(mContext))
        mAdapter.clear()
        mAdapter.addAll(list)
    }

    override fun showSearchData(data: ArrayList<HomeBean.Issue.Item>?) {
        erv_search_list.setPadding(0, 0, 0, 0)
        initAdapter(mAdapter, object : AdapterListener() {
            override fun onMoreShow() {
                getMore()
            }

            override fun onErrorClick() {
                mAdapter.resumeMore()
            }
        })
        if (erv_search_list.recyclerView.layoutManager !is LinearLayoutManager) {
            erv_search_list.setLayoutManager(LinearLayoutManager(mContext))
            mAdapter.clear()
        }
        data?.also { mAdapter.addAll(it) } ?: erv_search_list.showEmpty()
        tv_search_toast.text = String.format(resources.getString(R.string.search_result_count), et_search_content.text.toString().trim()
                , data?.let { it.size.toString() } ?: "0")
    }

    override fun createPresenter(): SearchPresenter = SearchPresenter(this)

    override fun getResId(): Int = R.layout.activity_search

    override fun initData() {
        mAdapter = object : RecyclerArrayAdapter<Any>(mContext) {
            val TYPE_HOTSEARCH = 1.shl(0)
            val TYPE_SEARCHDATA = 1.shl(1)
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return when (viewType) {
                    TYPE_HOTSEARCH -> HotSearchViewHolder(parent, mContext)
                    else -> HotViewHolder(parent, mContext)
                }
            }

            override fun getViewType(position: Int): Int {
                var item = mAdapter.getItem(position)
                if (item is String) {
                    return TYPE_HOTSEARCH
                }
                return TYPE_SEARCHDATA
            }

        }
        erv_search_list.adapter = mAdapter
        erv_search_list.setEmptyView(R.layout.view_empty)
        openKeyBord(et_search_content, mContext)
        mPresenter.getHotSearch()
    }

    private fun getMore() {
        mPresenter.getMore()
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = mAdapter.getItem(position)
            if (item is String) {
                mAdapter.clear()
                et_search_content.setText(item)
                et_search_content.setSelection(item.length)
                searchContent(item)
            }
        }
        et_search_content.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                if (et_search_content.text.isNullOrEmpty()) {
                    SingToast.showToast(mContext, "请输入你感兴趣的关键词")
                } else {
                    searchContent(et_search_content.text.toString())
                }
            }
            return@setOnEditorActionListener false
        }
        tv_search_cancel.setOnClickListener {
            closeKeyBord(et_search_content, mContext)
            finish()
        }
    }

    private fun searchContent(content: String) {
        mPresenter.getSearchData(content)
        closeKeyBord(et_search_content, mContext)
    }
}