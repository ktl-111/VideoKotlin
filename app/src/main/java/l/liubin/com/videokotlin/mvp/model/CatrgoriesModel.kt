package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import io.reactivex.functions.Function
import l.liubin.com.videokotlin.mvp.MyModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/10.
 */
class CatrgoriesModel : MyModel() {
    fun getCatrgories(function: Function<ArrayList<CategoryBean>, ArrayList<CategoryBean>>, observer: BaseObserver<ArrayList<CategoryBean>>) {
        universal(mApiService.getCategory().map(function), observer)
    }
}