package l.liubin.com.videokotlin.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import l.liubin.com.videokotlin.api.ApiEngine
import l.liubin.com.videokotlin.datebase.DownloadModel
import l.liubin.com.videokotlin.datebase.DownloadModel_Table
import l.liubin.com.videokotlin.download.DownloadListener
import l.liubin.com.videokotlin.download.DownloadState
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpHeaders
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by steam_lb on 2018/5/20/020.
 */
class DownloadService : Service() {
    lateinit var mExecutor: ThreadPoolExecutor
    var mDisposable = LinkedHashMap<String, Disposable>()
    var listeners = LinkedHashMap<String, DownloadListener>()

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

    fun addListener(url: String, downloadListener: DownloadListener) {
        listeners.put(url, downloadListener)
    }

    fun start(model: DownloadModel) {
        model.state = DownloadState.STATE_START
        model.update()
        var observable: Observable<Response<ResponseBody>> = if (model.currlength == 0.toLong()) {
            ApiEngine.apiEngine.getApiService().check(model.download_url)
        } else {
            ApiEngine.apiEngine.getApiService().download("bytes=${model.currlength}-${model.totallength}", model.download_url)
        }

        observable
                .map {
                    if (model.currlength == 0.toLong()) {
                        model.totallength = HttpHeaders.contentLength(it.headers())
                        val file = File(model.savepath)
                        if (file.exists()) {
                            file.delete()
                        }
                    }

                    writeFile(it.body()?.byteStream()!!, model)
                    ""
                }
                .subscribeOn(Schedulers.from(mExecutor))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: String) {
                    }

                    override fun onSubscribe(d: Disposable) {
                        mDisposable.put(model.download_url, d)
                    }
                })
    }

    fun writeFile(inputstream: InputStream, model: DownloadModel) {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(model.savepath, true)
            var b = ByteArray(1024 * 10)
            var len: Int = inputstream.read(b)
            while (len != -1) {
                fos.write(b, 0, len)
                len = inputstream.read(b)
                model.currlength += len
                println("${(model.currlength.toDouble() / model.totallength.toDouble()) * 100}   ${Thread.currentThread()}")
                progress(model)
            }
            model.state = DownloadState.STATE_SUCCESS
        } catch (e: IOException) {
            model.state = DownloadState.STATE_FAILED
        } finally {
            model.currlength = File(model.savepath).length()
            model.update()
            inputstream.close()
            fos?.close()
        }
    }

    private fun progress(model: DownloadModel) {
        Observable.just(model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    listeners[model.download_url]?.apply {
                        onProgress(model.currlength)
                    }
                }
    }

    override fun onBind(intent: Intent?): IBinder {
        return MyAnget()
    }

    inner class MyAnget : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }

    fun stop(url: String) {
        mDisposable[url]?.apply {
            dispose()
            var select = Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(url)).querySingle()
            select?.apply {
                state = DownloadState.STATE_PAUSE
                update()
            }
        }
    }
}