package l.liubin.com.videokotlin.utils

import android.content.Context
import com.tbruyelle.rxpermissions2.RxPermissions
import l.liubin.com.videokotlin.ui.base.BaseActivity

/**
 * Created by l on 2018/5/8.
 */
object RxPermissionsUtils {
    fun requestPermissions(context: Context, vararg permission: String, less: (Boolean) -> Unit) {
        val rxPermissions = RxPermissions(context as BaseActivity)
        permission?.let { rxPermissions.request(*permission) }
    }
}