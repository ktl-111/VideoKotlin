package l.liubin.com.videokotlin.manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Environment
import android.os.IBinder
import com.raizlabs.android.dbflow.sql.language.Select
import l.liubin.com.videokotlin.datebase.DownloadModel
import l.liubin.com.videokotlin.datebase.DownloadModel_Table
import l.liubin.com.videokotlin.download.DownloadListener
import l.liubin.com.videokotlin.download.DownloadState
import l.liubin.com.videokotlin.service.DownloadService
import l.liubin.com.videokotlin.utils.SingToast
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

    fun create(model: DownloadModel) {
        var select = Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(model.download_url)).querySingle()
        var curr_model = select ?: model?.let {
            it.insert()
            it
        }
        start(curr_model)
    }

    fun start(model: DownloadModel) {
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
                    }

                    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
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