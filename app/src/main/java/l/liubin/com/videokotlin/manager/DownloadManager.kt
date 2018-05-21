package l.liubin.com.videokotlin.manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.raizlabs.android.dbflow.sql.language.Select
import l.liubin.com.videokotlin.datebase.DownloadModel
import l.liubin.com.videokotlin.datebase.DownloadModel_Table
import l.liubin.com.videokotlin.download.DownloadListener
import l.liubin.com.videokotlin.download.DownloadState
import l.liubin.com.videokotlin.service.DownloadService
import l.liubin.com.videokotlin.utils.SingToast

/**
 * Created by l on 2018/5/21.
 */
class DownloadManager {
    lateinit var mContext: Context
    var downloadService: DownloadService? = null

    private constructor(context: Context) {
        mContext = context
    }

    companion object {
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

    fun stop(url: String) {
        downloadService?.stop(url)
    }

    fun start(model: DownloadModel) {
        var select = Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(model.download_url)).querySingle()
        var curr_mode =
                if (select != null) {
                    var toast = when (select.state) {
                        DownloadState.STATE_START -> {
                            SingToast.showToast(mContext, "正在下载")
                            return@start
                        }
                        DownloadState.STATE_SUCCESS -> {
                            SingToast.showToast(mContext, "以下载")
                            return@start
                        }
                        else -> "开始下载"
                    }
                    SingToast.showToast(mContext, toast)
                    select
                } else {
                    model.insert()
                    model
                }
        var intent = Intent(mContext, DownloadService::class.java)
        intent.putExtra(DownloadService.INTENT_DOWNLOAD, model)
        mContext.startService(intent)
        downloadService?.start(curr_mode)
                ?: mContext.bindService(intent, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                var anget = service as DownloadService.MyAnget
                downloadService = anget.getService()
                downloadService?.start(curr_mode)
                mContext.unbindService(this)
            }

        }, Context.BIND_AUTO_CREATE)
    }

    fun listenerProgrest(model: DownloadModel, downloadListener: DownloadListener) {
        var intent = Intent(mContext, DownloadService::class.java)
        intent.putExtra(DownloadService.INTENT_DOWNLOAD, model)
        mContext.startService(intent)
        downloadService?.addListener(model.download_url, downloadListener)
                ?: mContext.bindService(intent, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                var anget = service as DownloadService.MyAnget
                downloadService = anget.getService()
                downloadService?.addListener(model.download_url, downloadListener)
                mContext.unbindService(this)
            }

        }, Context.BIND_AUTO_CREATE)

    }
}