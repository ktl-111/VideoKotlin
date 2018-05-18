package l.liubin.com.videokotlin.api

import l.liubin.com.videokotlin.BuildConfig
import l.liubin.com.videokotlin.download.DownloadInterceptor
import l.liubin.com.videokotlin.download.DownloadListener
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by l on 2018/5/8.
 */
class ApiEngine {
    private val retrofit: Retrofit by lazy {
        val builder = OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        var listener = object : DownloadListener {
            override fun onStartDownload(url: String) {
                println("onStartDownload")
            }

            override fun onProgress(progress: Int) {
                println("onProgress  $progress")
            }

            override fun onFinishDownload() {
                println("onFinishDownload")
            }

            override fun onFail(errorInfo: String) {
                println("onFail")
            }

        }
        builder.addInterceptor(DownloadInterceptor(listener))
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        val apiEngine: ApiEngine by lazy { ApiEngine() }
    }

    fun getApiService(): Api = retrofit.create(Api::class.java)
}