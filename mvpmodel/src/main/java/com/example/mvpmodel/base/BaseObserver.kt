package l.liubin.com.videokotlin.mvp.base

import com.example.base.MyApplication
import com.example.mvpmodel.R
import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import l.liubin.com.videokotlin.utils.Utils
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by l on 2018/5/8.
 */
abstract class BaseObserver<T>(persenter: BasePresenter<*, BaseModel<*>>, mvpView: BaseView, isShowLoading: Boolean = true) : Observer<T> {
    private val mPersenter: BasePresenter<*, BaseModel<*>> = persenter
    private val mvpView: BaseView = mvpView
    private val isShowLoading: Boolean = isShowLoading

    override fun onSubscribe(d: Disposable) {
        mPersenter.addDisposable(d)
        onStart()
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    abstract fun onSuccess(data: T)

    private fun onStart() {
        if (isShowLoading) {
            mvpView.showLoading()
        }
    }

    private fun onEnd() {
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
        private const val NOT_FOUND = 404
        private const val INTERNAL_SERVER_ERROR = 500
        private const val UNSATISFIABLE_REQUEST = 504
        private const val SERVICE_TEMPORARILY_UNAVAILABLE = 503
        fun parseError(e: Throwable): String {
            var resid: Int
            when (e) {
                is HttpException -> resid = when (e.code()) {
                    NOT_FOUND -> R.string.message_not_found
                    INTERNAL_SERVER_ERROR -> R.string.message_internal_server_error
                    UNSATISFIABLE_REQUEST -> R.string.message_unsatisfiable_request
                    SERVICE_TEMPORARILY_UNAVAILABLE -> R.string.message_server_error
                    else -> R.string.message_unknown_mistake
                }
                is UnknownHostException -> //没有网络
                    resid = R.string.message_unknownhost
                is SocketTimeoutException -> // 连接超时
                    resid = R.string.message_sockettimeout
                is ConnectException -> resid = R.string.message_connectexception
                is ParseException -> resid = R.string.message_data_parsing_filed
                is JsonSyntaxException -> //解析失败
                    resid = R.string.message_data_parsing_filed
                is IOException -> resid = R.string.message_data_read_filed
                else -> resid = R.string.message_unknown_mistake
            }
            e?.let { e.printStackTrace() }
            return Utils.getStringFromResources(MyApplication.context, resid)
        }
    }
}