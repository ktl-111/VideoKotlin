package l.liubin.com.videokotlin.utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by l on 2018/5/8.
 */
object RxPermissionsUtils {
    fun requestPermissions(context: Context, vararg permission: String, less: (Boolean) -> Unit={}) {
        val rxPermissions = RxPermissions(context as AppCompatActivity)
        permission?.let { rxPermissions.request(*permission).subscribe(less) }
    }
}