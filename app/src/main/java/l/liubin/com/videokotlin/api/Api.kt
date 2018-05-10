package l.liubin.com.videokotlin.api

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by l on 2018/5/8.
 */
interface Api {
    companion object {
        val BASE_URL = "http://baobab.kaiyanapp.com/api/"
    }

    /**
     * 获取首页
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory(): Observable<ArrayList<CategoryBean>>

    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    fun getRankList(): Observable<TabInfoBean>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Observable<HomeBean.Issue>
}
