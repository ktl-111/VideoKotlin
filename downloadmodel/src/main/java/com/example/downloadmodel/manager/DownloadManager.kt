package com.example.downloadmodel.manager

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Environment
import android.os.IBinder
import com.example.downloadmodel.datebase.DownloadModel
import com.example.downloadmodel.datebase.DownloadModel_Table
import com.example.utils.NetworkUtils
import com.raizlabs.android.dbflow.sql.language.Select
import l.liubin.com.videokotlin.download.DownloadListener
import l.liubin.com.videokotlin.download.DownloadState
import l.liubin.com.videokotlin.service.DownloadService
import l.liubin.com.videokotlin.utils.SingToast
import l.liubin.com.videokotlin.utils.logD
import java.io.File

/**
 * Created by l on 2018/5/21.
 */
class DownloadManager {
    lateinit var mContext: Context
    var downloadService: DownloadService? = null

    private constructor(context: Context) {
        mContext = context
        var file = File(downloadPath)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    companion object {
        val downloadPath = Environment.getExternalStorageDirectory().getPath() + "/VideoKotlin/"
        var instance: DownloadManager? = null
        fun getInstance(context: Context): DownloadManager {
            return instance ?: createInstance(context)?.let {
                instance = it
                it
            }
        }

        @Synchronized
        private fun createInstance(context: Context): DownloadManager {
            return DownloadManager(context)
        }
    }

    fun stop(model: DownloadModel) {
        downloadService?.stop(model)
    }

    fun create(context: Context, model: DownloadModel) {
        var select = Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(model.download_url)).querySingle()
        var curr_model = select ?: model?.let {
            it.insert()
            it
        }
        if ((curr_model.state == DownloadState.STATE_PAUSE || curr_model.state == DownloadState.STATE_FAILED || curr_model.state == DownloadState.STATE_WAIT || curr_model.state == DownloadState.STATE_STOP)
                && !NetworkUtils.isWifiConnected(context)) {
            AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("当前正处于流量状态,是否需要下载")
                    .setPositiveButton("是") { _, _ ->
                        start(curr_model)
                    }
                    .setNegativeButton("否") { dialog, _ ->
                        dialog.dismiss()
                    }.setOnCancelListener { dialog ->
                        dialog.dismiss()
                    }.setCancelable(true)
                    .show()
        } else {
            start((curr_model))
        }
    }

    fun start(model: DownloadModel) {
        when (model.state) {
            DownloadState.STATE_START -> {
                SingToast.showToast(mContext, "正在下载")
                return@start
            }
            DownloadState.STATE_SUCCESS -> {
                SingToast.showToast(mContext, "当前视频以下载")
                return@start
            }
            DownloadState.STATE_DOWNLOAD -> {
                SingToast.showToast(mContext, "正在下载")
                return@start
            }
            DownloadState.STATE_PAUSE -> {
                SingToast.showToast(mContext, "开始下载")
            }
            DownloadState.STATE_FAILED -> {
                SingToast.showToast(mContext, "开始下载")
            }
            DownloadState.STATE_WAIT -> {
                SingToast.showToast(mContext, "开始下载")
            }
            DownloadState.STATE_STOP -> {
                SingToast.showToast(mContext, "开始下载")
            }
        }
        getService {
            it.start(model)
        }
    }

    fun listenerProgrest(model: DownloadModel, downloadListener: DownloadListener) {
        getService {
            it.addListener(model.download_url, downloadListener)
        }
    }

    fun getService(less: (DownloadService) -> Unit) {
        var intent = Intent(mContext, DownloadService::class.java)
        mContext.startService(intent)
        downloadService?.also(less)
                ?: mContext.bindService(intent, object : ServiceConnection {
                    override fun onServiceDisconnected(name: ComponentName?) {
                        logD(DownloadManager::class.java, "连接失败")
                    }

                    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                        logD(DownloadManager::class.java, "连接成功")
                        var anget = service as DownloadService.MyAnget
                        downloadService = anget.getService()
                        less(downloadService!!)
                        mContext.unbindService(this)
                    }

                }, Context.BIND_AUTO_CREATE)
    }

    fun clearListener() {
        downloadService?.clearListener()
    }

    fun remove(model: DownloadModel) {
        downloadService?.remoTask(model)
    }
}