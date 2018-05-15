package l.liubin.com.videokotlin.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.activity_categoriesdetails.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpActivity
import l.liubin.com.videokotlin.mvp.presenter.CatroiesDetailsPresenter
import l.liubin.com.videokotlin.mvp.view.CatroriesDetailsView
import l.liubin.com.videokotlin.utils.*
import l.liubin.com.videokotlin.viewholder.HotViewHolder

/**
 * Created by l on 2018/5/11.
 */
class CatrgoriesDetailsActivity : MvpActivity<CatroiesDetailsPresenter>(), CatroriesDetailsView {
    override fun addMore(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addAll(list)
    }

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

    override fun showDetailsList(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.clear()
        mAdapter.addAll(list)
    }

    override fun createPresenter(): CatroiesDetailsPresenter = CatroiesDetailsPresenter(this)

    companion object {
        val INTENT_BEAN = "intent_bean"
    }

    lateinit var bean: CategoryBean
    lateinit var mAdapter: RecyclerArrayAdapter<HomeBean.Issue.Item>
    var dHeight: Double = 0.0
    override fun getResId(): Int = R.layout.activity_categoriesdetails

    override fun initDataBefore() {
        super.initDataBefore()
        intent?.let { it.getSerializableExtra(INTENT_BEAN)?.let { bean = it as CategoryBean } }
        immersionBar.reset().titleBar(toolbar).init()
    }

    override fun initData() {
        setSupportActionBar(toolbar)
        supportActionBar?.let { it.setDisplayHomeAsUpEnabled(true) }
        dHeight = dip2px(mContext, 250f).toDouble() - mContext.resources.getDimensionPixelSize(R.dimen.title_bar_height).toDouble()
        tv_categoriesdetails_content.text = bean.description
        ctl_catrgoriesdetila_layout.title = bean.name
        GlideUils.loadImg(mContext, bean.headerImage, iv_categoriesdetails_img)
        erv_categoriesdetails_list.setLayoutManager(LinearLayoutManager(mContext))
        mAdapter = object : RecyclerArrayAdapter<HomeBean.Issue.Item>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return HotViewHolder(parent, mContext)
            }
        }
        erv_categoriesdetails_list.adapter = mAdapter
        initAdapter(mAdapter, object : AdapterListener() {
            override fun onMoreShow() {
                getMore()
            }

            override fun onErrorClick() {
                mAdapter.resumeMore()
            }

        })

        mPresenter.getDetailsList(bean.id)
    }

    fun getMore() {
        mPresenter.getMore()
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = mAdapter.getItem(position)
            startActivity(Intent(mContext, VideoDetailsActivity::class.java).putExtra(VideoDetailsActivity.INTENT_DATA, item))
        }



        toolbar.setNavigationOnClickListener { _ -> finish() }
        appbar_layout.addOnOffsetChangedListener { _, verticalOffset ->
            var offset = Math.abs(verticalOffset).toDouble() / dHeight
//            var hex = Integer.toHexString((Integer.parseInt("4D", 16).toDouble() * offset).toInt()).toUpperCase()
//            if (hex.length == 1) {
//                hex="0${hex}"
//            }
//            hex = "${hex}000000"
//            println(hex)
            blur_view.setBlurRadius(dip2px(mContext, 10f).toFloat() * offset.toFloat())
        }
    }
}