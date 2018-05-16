package l.liubin.com.videokotlin.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.activity_videodetails.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpActivity
import l.liubin.com.videokotlin.mvp.presenter.VideoPresenter
import l.liubin.com.videokotlin.mvp.view.VideoView
import l.liubin.com.videokotlin.utils.DisplayManager
import l.liubin.com.videokotlin.utils.SingToast
import l.liubin.com.videokotlin.viewholder.VideoDetailsContentViewHolder
import l.liubin.com.videokotlin.viewholder.VideoDetailsTitleViewHolder

/**
 * Created by l on 2018/5/15.
 */
class VideoDetailsActivity : MvpActivity<VideoPresenter>(), VideoView {
    lateinit var data: HomeBean.Issue.Item
    lateinit var mAdapter: RecyclerArrayAdapter<HomeBean.Issue.Item>

    companion object {
        val INTENT_DATA = "intent_data"
    }

    override fun initDataBefore() {
        super.initDataBefore()
        data = intent.getSerializableExtra(INTENT_DATA) as HomeBean.Issue.Item
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

    override fun showOrtherView(list: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.clear()
        mAdapter.addAll(list)
    }

    override fun createPresenter(): VideoPresenter = VideoPresenter(this)

    override fun getResId(): Int = R.layout.activity_videodetails

    override fun initData() {
        erv_videodetails_list.setLayoutManager(LinearLayoutManager(mContext))
        mAdapter = object : RecyclerArrayAdapter<HomeBean.Issue.Item>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return when (viewType) {
                    TYPE_TITLE -> VideoDetailsTitleViewHolder(parent, mContext)
                    else -> VideoDetailsContentViewHolder(parent, mContext)
                }
            }

            val TYPE_TITLE = 1.shl(1)
            val TAG_TITLE = "textCard"

            override fun getViewType(position: Int): Int {
                var item = mAdapter.getItem(position)
                if (item.type == TAG_TITLE) {
                    return TYPE_TITLE
                }
                return super.getViewType(position)
            }

        }
        erv_videodetails_list.adapter = mAdapter
        mPresenter.getOrtherData(data?.data?.id!!)
        DisplayManager.init(mContext)
        val backgroundUrl = data.data?.cover?.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let {
            var options = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .format(DecodeFormat.PREFER_ARGB_8888)
            Glide.with(mContext)
                    .asBitmap()
                    .load(it)
                    .apply(options)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            iv_videodatails_bg.setImageBitmap(resource)
                        }
                    })
        }
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = mAdapter.getItem(position)
            startActivity(Intent(mContext, VideoDetailsActivity::class.java).putExtra(VideoDetailsActivity.INTENT_DATA, item))
        }

    }
}