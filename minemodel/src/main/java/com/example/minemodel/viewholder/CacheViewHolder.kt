package com.example.minemodel.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.downloadmodel.datebase.DownloadModel
import com.example.downloadmodel.datebase.DownloadModel_Table
import com.example.downloadmodel.manager.DownloadManager
import com.example.minemodel.R
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.raizlabs.android.dbflow.sql.language.Select
import l.liubin.com.videokotlin.download.DownloadListener
import l.liubin.com.videokotlin.download.DownloadState
import l.liubin.com.videokotlin.utils.GlideUils
import l.liubin.com.videokotlin.utils.openVideo
import l.liubin.com.videokotlin.utils.toDoubleNumber

/**
 * Created by l on 2018/5/21.
 */
class CacheViewHolder(parent: ViewGroup, context: Context) : BaseViewHolder<DownloadModel>(parent, R.layout.itemview_cache) {
    var mContext = context
    var iv_img: ImageView
    var tv_title: TextView
    var tv_progress: TextView
    var iv_state: ImageView

    init {
        iv_img = `$`(R.id.iv_itemview_cache_img)
        tv_title = `$`(R.id.tv_itemview_cache_title)
        tv_progress = `$`(R.id.tv_itemview_cache_progress)
        iv_state = `$`(R.id.iv_itemview_cache_state)
    }

    override fun setData(initData: DownloadModel) {
        var data = Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(initData.download_url)).querySingle()
        iv_state.setOnClickListener {
            var model = Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(initData.download_url)).querySingle()
            model?.also {
                when (it.state) {
                    DownloadState.STATE_START -> {
//                        it.state = DownloadState.STATE_PAUSE
                        DownloadManager.getInstance(mContext).stop(it)
                    }
                    DownloadState.STATE_FAILED -> {
//                        it.state = DownloadState.STATE_START
                        DownloadManager.getInstance(mContext).start(it)
                    }
                    DownloadState.STATE_PAUSE -> {
//                        it.state = DownloadState.STATE_START
                        DownloadManager.getInstance(mContext).start(it)
                    }
                    DownloadState.STATE_STOP -> {
//                        it.state = DownloadState.STATE_START
                        DownloadManager.getInstance(mContext).start(it)
                    }
                    DownloadState.STATE_DOWNLOAD -> {
//                        it.state = DownloadState.STATE_PAUSE
                        DownloadManager.getInstance(mContext).stop(it)
                    }
                    DownloadState.STATE_WAIT -> {
//                        it.state = DownloadState.STATE_STOP
                        DownloadManager.getInstance(mContext).remove(it)
                    }
                    DownloadState.STATE_SUCCESS -> {
                        openVideo(mContext, it.savepath)
                    }
                }
                it.update()
            }
        }
        data?.also {
            GlideUils.loadImg(mContext, it.img_url, iv_img)
            iv_state.visibility = View.VISIBLE
            tv_title.text = it.title
            iv_state.tag = data.download_url
            tv_progress.tag = data.download_url
            setState(it)
            DownloadManager.getInstance(mContext)
                    .listenerProgrest(it, object : DownloadListener() {
                        override fun onFinishDownload(model: DownloadModel) {
                            setState(model)
                        }

                        override fun onProgress(model: DownloadModel) {
                            setState(model)
                        }

                        override fun onStartDownload(model: DownloadModel) {
                            setState(model)
                        }

                        override fun onFiled(model: DownloadModel) {
                            setState(model)
                        }

                        override fun onPause(model: DownloadModel) {
                            setState(model)
                        }

                        override fun onWait(model: DownloadModel) {
                            setState(model)
                        }

                        override fun onStop(model: DownloadModel) {
                            setState(model)
                        }
                    })
        }
    }

    fun setState(model: DownloadModel, errorinfo: String = "") {
        if (iv_img.tag != model.download_url && tv_progress.tag != model.download_url) {
            return
        }
        when (model.state) {
            DownloadState.STATE_STOP -> {
                iv_state.setBackgroundResource(R.mipmap.start)
                tv_progress.text = "已停止下载"
            }
            DownloadState.STATE_DOWNLOAD -> {
                iv_state.setBackgroundResource(R.mipmap.pause)
                tv_progress.text = "下载中:${((model.currlength.toDouble() / model.totallength.toDouble()) * 100).toDoubleNumber()}%"
            }
            DownloadState.STATE_START -> {
                iv_state.setBackgroundResource(R.mipmap.pause)
                tv_progress.text = "开始下载"
            }
            DownloadState.STATE_WAIT -> {
                iv_state.setBackgroundResource(R.mipmap.pause)
                tv_progress.text = "等待下载"
            }
            DownloadState.STATE_FAILED -> {
                iv_state.setBackgroundResource(R.mipmap.start)
                tv_progress.text = "下载失败:${errorinfo}"
            }
            DownloadState.STATE_PAUSE -> {
                iv_state.setBackgroundResource(R.mipmap.start)
                tv_progress.text = "暂停下载"
            }
            DownloadState.STATE_SUCCESS -> {
                iv_state.setBackgroundResource(R.mipmap.ic_launcher)
                tv_progress.text = "下载完成"
            }
        }
    }
}