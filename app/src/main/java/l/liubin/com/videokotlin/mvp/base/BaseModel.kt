package l.liubin.com.videokotlin.mvp.base

import io.reactivex.Observable
import l.liubin.com.videokotlin.api.Api
import l.liubin.com.videokotlin.api.ApiEngine
import l.liubin.com.videokotlin.utils.RxUtils
import java.util.concurrent.TimeUnit

/**
 * Created by l on 2018/5/8.
 */
class BaseModel {

    val mApiService: Api by lazy {
        ApiEngine.apiEngine.getApiService()
    }

    companion object {
        val delayTime: Long = 500
        fun universal(observer: Observable<*>, baseObserver: BaseObserver<Any>, isDelay: Boolean = false) {
            if (isDelay) {
                observer.delay(delayTime, TimeUnit.MILLISECONDS)
            }
            observer.compose(RxUtils.rxSchedulerHelper())
                    .subscribe(baseObserver)
        }
    }
}