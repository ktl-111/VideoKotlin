package l.liubin.com.videokotlin.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by l on 2018/5/9.
 * 单例模式
 */

class SingToast() {
    companion object {
        var instance: Toast? = null
        @Synchronized
        fun getInstance(context: Context, msg: String): Toast {
            instance?.let { instance?.setText(msg) }
                    ?: Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                    ?.let { instance = it }
            return instance!!
        }

        fun showToast(context: Context, msg: String) {
            getInstance(context, msg).show()
        }
    }
}