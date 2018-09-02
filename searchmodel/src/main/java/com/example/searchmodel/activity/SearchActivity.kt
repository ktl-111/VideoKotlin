package com.example.searchmodel.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.example.searchmodel.R
import com.example.searchmodel.mvp.persenter.SearchPresenter
import com.example.searchmodel.mvp.view.SearchView
import com.example.videodetailsmodel.activity.VideoDetailsActivity
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.activity_search.*
import l.liubin.com.videokotlin.mvp.base.MvpActivity
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

    var tag = false

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
        }
        if (tag) {
            mAdapter.clear()
            tag = !tag
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
        initRecyclerView(erv_search_list)
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
                et_search_content.setText(item)
                et_search_content.setSelection(item.length)
                searchContent(item)
            } else if (item is HomeBean.Issue.Item) {
                startActivity(Intent(mContext, VideoDetailsActivity::class.java).putExtra(VideoDetailsActivity.INTENT_DATA, item))
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
        tag = true
        mPresenter.getSearchData(content)
        closeKeyBord(et_search_content, mContext)
    }
}