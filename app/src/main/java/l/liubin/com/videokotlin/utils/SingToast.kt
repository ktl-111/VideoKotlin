package l.liubin.com.videokotlin.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by l on 2018/5/9.
 * 单例模式
 */

object SingToast {
    var instance: Toast? = null

    fun getInstance(context: Context, msg: String): Toast {
        instance?.let { instance?.setText(msg) }
                ?: createToast(context, msg)
                        ?.let { instance = it }
        return instance!!
    }

    @Synchronized
    private fun createToast(context: Context, msg: String): Toast {
        return instance?.let { instance } ?: Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    }

    fun showToast(context: Context, msg: String) {
        getInstance(context, msg).show()
    }
}