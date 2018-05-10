package l.liubin.com.videokotlin.mvp.base

import io.reactivex.Observable
import l.liubin.com.videokotlin.api.Api
import l.liubin.com.videokotlin.api.ApiEngine
import l.liubin.com.videokotlin.utils.RxUtils
import java.util.concurrent.TimeUnit

/**
 * Created by l on 2018/5/8.
 */
open class BaseModel {

    val mApiService: Api by lazy {
        ApiEngine.apiEngine.getApiService()
    }

    companion object {
        val delayTime: Long = 500
        //observer 的泛型要定义不能用*,不然不知道发送者的类型和接受者的类型一不一致
        fun <T : Any> universal(observer: Observable<T>, baseObserver: BaseObserver<T>, isDelay: Boolean =true) {
            if (isDelay) {
                observer.delay(delayTime, TimeUnit.MILLISECONDS)
            }

            observer.compose(RxUtils.rxSchedulerHelper())
                    .subscribe(baseObserver)
        }
    }
}
