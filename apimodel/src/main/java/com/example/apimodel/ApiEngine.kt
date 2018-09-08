package l.liubin.com.videokotlin.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by l on 2018/5/8.
 */
class ApiEngine {
    private val retrofit: Retrofit by lazy {
        val builder = getOkHttpBuidler()
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    companion object {
        val apiEngine: ApiEngine by lazy { ApiEngine() }
        fun getOkHttpBuidler(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
                    .connectTimeout(12, TimeUnit.SECONDS)
                    .writeTimeout(12, TimeUnit.SECONDS)
                    .writeTimeout(12, TimeUnit.SECONDS)
            //下载不能加这个,不然的话会网络拿完数据才进行读写操作
//            if (BuildConfig.DEBUG) {
//                builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            }
//             builder.addInterceptor(DownloadInterceptor())
            return builder
        }
    }

    fun <T> getApiService(clazz: Class<T>): T = retrofit.create(clazz)
}