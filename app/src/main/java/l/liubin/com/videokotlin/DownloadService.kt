package l.liubin.com.videokotlin

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import l.liubin.com.videokotlin.datebase.DownloadModel
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by steam_lb on 2018/5/20/020.
 */
class DownloadService : Service() {
    lateinit var mExecutor: ThreadPoolExecutor

    companion object {
        val INTENT_DOWNLOAD = "intent_download"
    }

    override fun onCreate() {
        var unit = TimeUnit.MILLISECONDS
        var workQueue = LinkedBlockingDeque<Runnable>()
        var threadFactory = Executors.defaultThreadFactory()
        var handler = ThreadPoolExecutor.AbortPolicy()
        mExecutor = ThreadPoolExecutor(3, // 执行线程的线程数
                3, // 在线程池中最多能创建多少个线程
                10,// 表示线程没有任务执行时最多保持多久时间会终
                unit, // 时间单位
                workQueue, // 缓存队列,用来存放等待执行的任务
                threadFactory, // 线程工厂,创建线程
                handler)// 异常捕获器
    }

    fun start() {

    }

    override fun onBind(intent: Intent?): IBinder {
        return MyAnget()
    }

    inner class MyAnget : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }
}