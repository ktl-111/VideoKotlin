package l.liubin.com.videokotlin.utils

import android.support.v4.content.ContextCompat
import l.liubin.com.videokotlin.App.MyApplication

/**
 * Created by l on 2018/5/9.
 */
object Utils {
    fun getStringFromResources(resId: Int, vararg format: Any?): String {
        return format?.let { MyApplication.context.resources.getString(resId, format) }
                ?: MyApplication.context.resources.getString(resId)
    }

    fun getColor(resId: Int): Int = ContextCompat.getColor(MyApplication.context, resId)

}