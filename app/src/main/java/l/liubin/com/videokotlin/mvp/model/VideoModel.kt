package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.MyModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by l on 2018/5/15.
 */
class VideoModel : MyModel() {
    fun getOrtherData(id: Long, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getRelatedData(id), baseObserver)
    }

    fun checkUrl(url: String, baseObserver: BaseObserver<Response<ResponseBody>>) {
        universal(mApiService.check(url), baseObserver)
    }
}