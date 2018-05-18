package l.liubin.com.videokotlin.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.makeramen.roundedimageview.RoundedImageView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_videodetails.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.api.ApiEngine
import l.liubin.com.videokotlin.mvp.base.MvpActivity
import l.liubin.com.videokotlin.mvp.presenter.VideoPresenter
import l.liubin.com.videokotlin.mvp.view.VideoView
import l.liubin.com.videokotlin.utils.*
import l.liubin.com.videokotlin.viewholder.VideoDetailsContentViewHolder
import l.liubin.com.videokotlin.viewholder.VideoDetailsTitleViewHolder
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

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
        var tv_close: TextView = headerView.findViewById(R.id.tv_close)
        var dis = hashMapOf<String, Disposable>()
        tv_close.setOnClickListener { _ ->
            dis[data.data?.playUrl!!]?.dispose()
        }
        RxPermissionsUtils.requestPermissions(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE) { b: Boolean -> SingToast.showToast(mContext, "$b") }
        tv_download.setOnClickListener { _ ->

            var unit = TimeUnit.MILLISECONDS
            var workQueue = LinkedBlockingDeque<Runnable>()
            var threadFactory = Executors.defaultThreadFactory()
            var handler = ThreadPoolExecutor.AbortPolicy()
            var executor = ThreadPoolExecutor(3, // 执行线程的线程数
                    3, // 在线程池中最多能创建多少个线程
                    10,// 表示线程没有任务执行时最多保持多久时间会终
                    unit, // 时间单位
                    workQueue, // 缓存队列,用来存放等待执行的任务
                    threadFactory, // 线程工厂,创建线程
                    handler)// 异常捕获器
            ApiEngine.apiEngine.getApiService().download(data.data?.playUrl!!)
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        var path = Environment.getExternalStorageDirectory().path + "/测试.mp4"
                        writeFile(it.byteStream(), path)
                        "成功"
                    }
                    .subscribe(object : Observer<String> {
                        override fun onNext(t: String) {
                            println("onNext$t")
                        }

                        override fun onSubscribe(d: Disposable) {

                            dis.put(data.data?.playUrl!!, d)
                        }

                        override fun onComplete() {
                        }

                        override fun onError(e: Throwable) {
                        }

                    })

//            (1 until 10).forEach {
//                Observable.create(object : ObservableOnSubscribe<String> {
//                    override fun subscribe(e: ObservableEmitter<String>) {
//                        println("执行前$it")
//                        var time: Long = System.currentTimeMillis()
//                        while (true) {
//                            if (System.currentTimeMillis() - time > 5000) {
//                                break
//                            }
//                            if (e.isDisposed) {
//                                println("$it 已销毁")
//                                return
//                            }
//                        }
//                        println("执行后$it")
//                        e.onNext("$it   ${System.currentTimeMillis()}  ${Thread.currentThread()}")
//                    }
//                }).subscribeOn(Schedulers.from(executor))
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(object : Observer<String> {
//                            override fun onComplete() {
//                            }
//
//                            override fun onSubscribe(d: Disposable) {
//                                dis.put(it, d)
//                                println("Disposable集合大小 ${dis.size}")
//                            }
//
//                            override fun onNext(t: String) {
//                                println("onNext$t")
//                            }
//
//                            override fun onError(e: Throwable) {
//                            }
//                        })
//            }

        }
    }

    fun writeFile(inputstream: InputStream, filePath: String) {
        var file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            var b = ByteArray(1024)
            var len: Int = inputstream.read(b)
            while (len != -1) {
                fos.write(b, 0, len)
                len = inputstream.read(b)
            }

        } catch (e: Exception) {

        } finally {
            inputstream.close()
            fos?.close()
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

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = mAdapter.getItem(position)
            startActivity(Intent(mContext, VideoDetailsActivity::class.java).putExtra(VideoDetailsActivity.INTENT_DATA, item))
        }

    }
}