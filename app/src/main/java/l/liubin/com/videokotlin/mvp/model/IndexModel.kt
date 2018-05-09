package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import l.liubin.com.videokotlin.mvp.base.BaseModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.utils.RxUtils

/**
 * Created by l on 2018/5/9.
 */
class IndexModel : BaseModel() {
    fun getIndexList(num: Int, funcation: Function<in HomeBean, out ObservableSource<out HomeBean>>, baseObserver: BaseObserver<HomeBean>) {
        universal(mApiService.getFirstHomeData(num).flatMap(funcation)
                , baseObserver)
    }

    fun getIndexMore(nextUrl: String): Observable<HomeBean>
            = mApiService.getMoreHomeData(nextUrl).compose(RxUtils.rxSchedulerHelper())

}