package l.liubin.com.videokotlin.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.gyf.barlibrary.BarHide
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.makeramen.roundedimageview.RoundedImageView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_videodetails.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.datebase.DownloadModel
import l.liubin.com.videokotlin.manager.DownloadManager
import l.liubin.com.videokotlin.mvp.base.MvpActivity
import l.liubin.com.videokotlin.mvp.presenter.VideoPresenter
import l.liubin.com.videokotlin.mvp.view.VideoView
import l.liubin.com.videokotlin.utils.DisplayManager
import l.liubin.com.videokotlin.utils.GlideUils
import l.liubin.com.videokotlin.utils.SingToast
import l.liubin.com.videokotlin.utils.durationFormat
import l.liubin.com.videokotlin.viewholder.VideoDetailsContentViewHolder
import l.liubin.com.videokotlin.viewholder.VideoDetailsTitleViewHolder

/**
 * Created by l on 2018/5/15.
 */
class VideoDetailsActivity : MvpActivity<VideoPresenter>(), VideoView, RecyclerArrayAdapter.ItemView {
    override fun onCreateView(parent: ViewGroup?): View {
        return layoutInflater.inflate(R.layout.itemview_videodetails_info, parent, false)
    }

    lateinit var tv_title: TextView
    lateinit var tv_tag: TextView
    lateinit var tv_content: TextView
    lateinit var tv_usertag: TextView
    lateinit var tv_download: TextView
    lateinit var riv_img: RoundedImageView


    override fun onBindView(headerView: View) {
        tv_title = headerView.findViewById(R.id.tv_itemview_videodetails_info_title)
        tv_content = headerView.findViewById(R.id.tv_itemview_videodetails_info_content)
        tv_usertag = headerView.findViewById(R.id.tv_itemview_videodetails_info_usertag)
        tv_tag = headerView.findViewById(R.id.tv_itemview_videodetails_info_tag)
        tv_download = headerView.findViewById(R.id.tv_itemview_videodetails_info_download)
        riv_img = headerView.findViewById(R.id.riv_itemview_videodetails_info_avatar)
        tv_title.text = data.data?.title
        tv_tag.text = "#${data.data?.category} / ${durationFormat(data.data?.duration)}"
        data.data?.description?.also { tv_content.text = it }
        var content = SpannableString("${data.data?.author?.name}\n${data.data?.author?.description}")
        content.setSpan(RelativeSizeSpan(0.8f), data.data?.author?.name?.length!!, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        content.setSpan(ForegroundColorSpan(Color.parseColor("#aaaaaa")), data.data?.author?.name?.length!!, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_usertag.text = content
        GlideUils.loadImg(mContext, data.data?.author?.icon!!, riv_img)
        tv_download.setOnClickListener { _ ->
            var model = DownloadModel()
            model.download_url = data.data?.playUrl
            model.title = data.data?.title
            model.img_url = data.data?.cover?.feed
            model.savepath = "${DownloadManager.downloadPath}${data.data?.title}.mp4"
            DownloadManager.getInstance(mContext)
                    .create(model)
        }
    }

    lateinit var data: HomeBean.Issue.Item
    lateinit var mAdapter: RecyclerArrayAdapter<HomeBean.Issue.Item>

    companion object {
        val INTENT_DATA = "intent_data"
    }

    override fun initDataBefore() {
        super.initDataBefore()
        data = intent.getSerializableExtra(INTENT_DATA) as HomeBean.Issue.Item
        immersionBar
                .reset()
                .statusBarDarkFont(true)
                .statusBarAlpha(0.0f)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init()
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
        initVideoData()
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
        mAdapter.addHeader(this)

        mPresenter.getOrtherData(data?.data?.id!!)
    }

    var mOrientationUtils: OrientationUtils? = null
    private fun initVideoData() {
        mOrientationUtils = OrientationUtils(this, gsy_video)
        gsy_video.isRotateViewAuto = false

        var imagView = ImageView(mContext)
        GlideUils.loadImg(mContext, data.data?.cover?.feed!!, imagView)
        var option = GSYVideoOptionBuilder()
        option.setThumbImageView(imagView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(data.data?.playUrl)
                .setCacheWithPlay(false)
                .setVideoTitle(data.data?.videoTitle)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any?) {
                        super.onPrepared(url, *objects)
                        mOrientationUtils?.isEnable = true
                        isPlay = true
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                        super.onQuitFullscreen(url, *objects)
                        mOrientationUtils?.backToProtVideo()
                    }
                }).setLockClickListener { view, lock ->
            mOrientationUtils?.isEnable = !lock
        }.build(gsy_video)

        gsy_video.fullscreenButton.setOnClickListener {
            mOrientationUtils?.resolveByClick()
            gsy_video.startWindowFullscreen(mContext, true, true)
        }

    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = mAdapter.getItem(position)
            startActivity(Intent(mContext, VideoDetailsActivity::class.java).putExtra(VideoDetailsActivity.INTENT_DATA, item))
        }
    }

    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            gsy_video.onConfigurationChanged(this, newConfig, mOrientationUtils)
        }
    }

    override fun onBackPressed() {
        mOrientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(mContext)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        getCurrPlay().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        getCurrPlay().onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurrPlay().release()
        }
        mOrientationUtils?.releaseListener()
    }

    fun getCurrPlay(): GSYVideoPlayer {
        return gsy_video.fullWindowPlayer?.let { it } ?: gsy_video
    }

}