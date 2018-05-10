package l.liubin.com.videokotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.fragment_hotlist.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpFragment
import l.liubin.com.videokotlin.mvp.presenter.HotPresenter
import l.liubin.com.videokotlin.mvp.view.HotView
import l.liubin.com.videokotlin.utils.SingToast

/**
 * Created by l on 2018/5/10.
 */
class HotListFragment : MvpFragment<HotPresenter>(), HotView {
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

    override fun showList(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.clear()
        mAdapter.addAll(list)
    }

    override fun createPresent(): HotPresenter = HotPresenter(this)

    companion object {
        val ARG_URL = "arg_url"
        fun getInstance(url: String): HotListFragment {
            var fragment = HotListFragment()
            val bundle = Bundle()
            bundle.putString(ARG_URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var apiUrl: String? = null
    lateinit var mAdapter: RecyclerArrayAdapter<HomeBean.Issue.Item>

    init {
        arguments?.getString(ARG_URL)?.let { apiUrl = it }
    }

    override fun getResId(): Int = R.layout.fragment_hotlist

    override fun initData() {
        erv_hot_list.setLayoutManager(LinearLayoutManager(mContext))
        mAdapter = object : RecyclerArrayAdapter<HomeBean.Issue.Item>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        erv_hot_list.adapter = mAdapter
    }

    override fun initEvent() {
    }
}