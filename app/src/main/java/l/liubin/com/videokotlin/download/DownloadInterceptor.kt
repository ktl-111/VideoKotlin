package l.liubin.com.videokotlin.download

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by l on 2018/5/18.
 */
class DownloadInterceptor(var downloadListener: DownloadListener? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        println("intercept---------${chain.request().url()}")
        return response.newBuilder().body(DownloadResponseBody(response.body()!!, downloadListener)).build()
    }
}