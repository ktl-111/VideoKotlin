package l.liubin.com.videokotlin.api

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by l on 2018/5/8.
 */
interface Api {
    companion object {
        val BASE_URL = "http://baobab.kaiyanapp.com/api/"
    }

    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>
}