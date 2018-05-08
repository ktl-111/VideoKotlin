package l.liubin.com.videokotlin.mvp.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

/**
 * Created by l on 2018/5/8.
 */
abstract class BaseObserver<T>(persenter: BasePresenter<*, BaseModel>, mvpView: BaseView, isShowLoading: Boolean) : Observer<T> {
    val mPersenter: BasePresenter<*, BaseModel> = persenter
    val mvpView: BaseView = mvpView
    val isShowLoading: Boolean = isShowLoading

    constructor(persenter: BasePresenter<*, BaseModel>, mvpView: BaseView) : this(persenter, mvpView, false)

    override fun onSubscribe(d: Disposable) {
        mPersenter.addDisposable(d)
        onStart()
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    abstract fun onSuccess(data: T)

    fun onStart() {
        if (isShowLoading) {
            mvpView.showLoading()
        }
    }

    fun onEnd() {
        if (isShowLoading) {
            mvpView.hindeLoading()
        }
    }

    fun onError(msg: String) {
        mvpView.onError(msg)
    }

    override fun onError(e: Throwable) {
        onError(parseError(e))
        onEnd()
    }

    override fun onComplete() {
        onEnd()
    }

    companion object {
        val NOT_FOUND = 404
        val INTERNAL_SERVER_ERROR = 500
        val UNSATISFIABLE_REQUEST = 504
        val SERVICE_TEMPORARILY_UNAVAILABLE = 503
        fun parseError(e: Throwable): String {
            var msg: String = ""
            if (e is HttpException) {
//                msg = when (e.code()){
//                    NOT_FOUND->
//                }
            }
            return msg
        }
    }
}