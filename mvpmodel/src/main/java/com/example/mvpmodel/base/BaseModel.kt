package l.liubin.com.videokotlin.mvp.base

import io.reactivex.Observable
import l.liubin.com.videokotlin.api.ApiEngine
import l.liubin.com.videokotlin.utils.RxUtils
import java.util.concurrent.TimeUnit

/**
 * Created by steam_l on 2018/8/31.
 * Desprition :泛型T为对应model设置的api类,这样每个model都有自己的api类
 */
abstract class BaseModel<T> {

    val mApiService: T by lazy {
        ApiEngine.apiEngine.getApiService(getClazz())
    }

    abstract fun getClazz(): Class<T>

    companion object {
        private val delayTime: Long = 500
        //observer 的泛型要定义不能用*,不然不知道发送者的类型和接受者的类型一不一致
        fun <T : Any> universal(observer: Observable<T>, baseObserver: BaseObserver<T>, isDelay: Boolean = true) {
            var obser = observer
            if (isDelay) {
                obser = observer.delay(delayTime, TimeUnit.MILLISECONDS)
            }
            obser.compose(RxUtils.rxSchedulerHelper())
                    .subscribe(baseObserver)
        }
    }
}
