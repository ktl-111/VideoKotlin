package l.liubin.com.videokotlin.utils

import l.liubin.com.videokotlin.App.MyApplication

/**
 * Created by l on 2018/5/9.
 */
object Utils {
    fun getStringFromResources(resId: Int, vararg format: Any?): String {
        return if (format == null) {
            MyApplication.context.resources.getString(resId)
        } else {
            MyApplication.context.resources.getString(resId, format)
        }
    }

}