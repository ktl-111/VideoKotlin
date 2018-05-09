package l.liubin.com.videokotlin.mvp.base

import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import l.liubin.com.videokotlin.R
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
abstract class BaseObserver<T>(persenter: BasePresenter<*, BaseModel>, mvpView: BaseView, isShowLoading: Boolean = true) : Observer<T> {
    private val mPersenter: BasePresenter<*, BaseModel> = persenter
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
            var msg: String
            when (e) {
                is HttpException -> msg = when (e.code()) {
                    NOT_FOUND -> Utils.getStringFromResources(R.string.message_not_found)
                    INTERNAL_SERVER_ERROR -> Utils.getStringFromResources(R.string.message_internal_server_error)
                    UNSATISFIABLE_REQUEST -> Utils.getStringFromResources(R.string.message_unsatisfiable_request)
                    SERVICE_TEMPORARILY_UNAVAILABLE -> Utils.getStringFromResources(R.string.message_server_error)
                    else -> ""
                }
                is UnknownHostException -> //没有网络
                    msg = Utils.getStringFromResources(R.string.message_unknownhost)
                is SocketTimeoutException -> // 连接超时
                    msg = Utils.getStringFromResources(R.string.message_sockettimeout)
                is ConnectException -> msg = Utils.getStringFromResources(R.string.message_connectexception)
                is ParseException -> msg = Utils.getStringFromResources(R.string.message_data_parsing_filed)
                is JsonSyntaxException -> //解析失败
                    msg = Utils.getStringFromResources(R.string.message_data_parsing_filed)
                is IOException -> msg = Utils.getStringFromResources(R.string.message_data_read_filed)
                else -> msg = Utils.getStringFromResources(R.string.message_unknown_mistake)
            }
            e?.let { e.printStackTrace() }
            return msg
        }
    }
}