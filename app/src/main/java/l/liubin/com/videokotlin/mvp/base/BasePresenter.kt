package l.liubin.com.videokotlin.mvp.base

import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by l on 2018/5/8.
 */
abstract class BasePresenter<V, out M : BaseModel>(mvpView: V) {
    var mView: V? = mvpView//对应的view
    val mGson: Gson by lazy { Gson() }//json解析
    val mModel: M by lazy { createModel() }//对应的model
    val mDisposable: CompositeDisposable by lazy { CompositeDisposable() }//延迟加载,rxjava中观察者对象和观察者直接的通道,用于切断作用

    abstract fun createModel(): M

    fun detachView() {
        this.mView = null
        clearDisposable()
    }

    fun clearDisposable() {
        if (mDisposable.size() != 0) {
            mDisposable.clear()
        }
    }

    fun addDisposable(disposable: Disposable) {
        mDisposable.add(disposable)
    }

}