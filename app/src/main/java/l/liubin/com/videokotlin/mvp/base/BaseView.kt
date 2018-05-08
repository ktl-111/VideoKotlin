package l.liubin.com.videokotlin.mvp.base

/**
 * Created by l on 2018/5/8.
 */
interface BaseView {
    fun onSuccess(msg: String)
    fun onError(msg: String)
    fun showLoading()
    fun hindeLoading()
}