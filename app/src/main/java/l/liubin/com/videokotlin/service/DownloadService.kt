package l.liubin.com.videokotlin.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import l.liubin.com.videokotlin.api.ApiEngine
import l.liubin.com.videokotlin.datebase.DownloadModel
import l.liubin.com.videokotlin.download.DownloadListener
import l.liubin.com.videokotlin.download.DownloadState
import l.liubin.com.videokotlin.mvp.MyModel
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
    var listeners = LinkedHashMap<String, DownloadListener>()
    var states = LinkedHashMap<String, DownloadModel>()
    var mDisposables = LinkedHashMap<String, Disposable>()
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
        model.state = DownloadState.STATE_WAIT
        model.update()
        senBroadcast(model)
        states[model.download_url] = model
        var observable: Observable<Response<ResponseBody>> = if (model.currlength == 0.toLong()) {
            //第一次下载,直接拿
            ApiEngine.apiEngine.getApiService(MyModel.apiClazz).check(model.download_url)
        } else {
            //第二次下载,要加range
            ApiEngine.apiEngine.getApiService(MyModel.apiClazz).download("bytes=${model.currlength}-${model.totallength}", model.download_url)
        }

        observable
                .map {
                    model.state = DownloadState.STATE_START
                    if (model.currlength == 0.toLong()) {
                        model.totallength = HttpHeaders.contentLength(it.headers())
                        val file = File(model.savepath)
                        if (file.exists()) {
                            file.delete()
                        }
                    }
                    model.update()
                    senBroadcast(model)
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
                        mDisposables.put(model.download_url, d)
                    }
                })
    }

    fun writeFile(inputstream: InputStream, model: DownloadModel) {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(model.savepath, true)
            var b = ByteArray(1024 * 10)
            var len: Int = inputstream.read(b)
            model.state = DownloadState.STATE_DOWNLOAD
            model.update()
            while (len != -1) {
                if (states[model.download_url]?.state == DownloadState.STATE_PAUSE) {//当前状态如果是暂停就break
                    model.state = DownloadState.STATE_PAUSE
                    break
                }
                fos.write(b, 0, len)
                len = inputstream.read(b)
                model.currlength += len
                //通知更新
                senBroadcast(model)
            }
        } catch (e: IOException) {
            model.state = DownloadState.STATE_FAILED
        } finally {//最后finally的时候记录当前文件长度,以及更新数据库
            model.currlength = File(model.savepath).length()
            if (model.currlength == model.totallength) {
                model.state = DownloadState.STATE_SUCCESS
            }
            model.update()
            senBroadcast(model)
            inputstream.close()
            fos?.close()
        }
    }

    private fun senBroadcast(model: DownloadModel) {
        when (model.state) {
            DownloadState.STATE_PAUSE -> sendMainThread(model, DownloadListener::onPause)
            DownloadState.STATE_START -> sendMainThread(model, DownloadListener::onStartDownload)
            DownloadState.STATE_SUCCESS -> sendMainThread(model, DownloadListener::onFinishDownload)
            DownloadState.STATE_FAILED -> sendMainThread(model, DownloadListener::onFiled)
            DownloadState.STATE_DOWNLOAD -> sendMainThread(model, DownloadListener::onProgress)
            DownloadState.STATE_WAIT -> sendMainThread(model, DownloadListener::onWait)
            DownloadState.STATE_STOP -> sendMainThread(model, DownloadListener::onStop)
        }
    }

    private fun sendMainThread(model: DownloadModel, less: DownloadListener.(DownloadModel) -> Unit) {
        Observable.just(model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    listeners[model.download_url]?.less(model)
                }
    }

    fun clearListener() {
        listeners?.clear()
    }

    fun remoTask(model: DownloadModel) {
        mDisposables[model.download_url]?.also {
            model.state = DownloadState.STATE_STOP
            model.update()
            senBroadcast(model)
            it.dispose()
            mDisposables.remove(model.download_url)
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return MyAnget()
    }

    inner class MyAnget : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }

    fun stop(model: DownloadModel) {
        states[model.download_url]?.also {
            it.state = DownloadState.STATE_PAUSE
        }
    }
}