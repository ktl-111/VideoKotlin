package l.liubin.com.videokotlin.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.utils.R
import com.jude.easyrecyclerview.EasyRecyclerView
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import java.io.File
import java.math.BigDecimal
import java.text.DecimalFormat


/**
 * Created by l on 2018/5/9.
 */
object Utils {

    fun getStringFromResources(context: Context, resId: Int, vararg format: Any?): String {
        return format?.let { context.resources.getString(resId, format) }
                ?: context.resources.getString(resId)
    }

    fun getColor(context: Context, resId: Int): Int = ContextCompat.getColor(context, resId)
}

fun logD(clazz: Class<*>, mess: String) {
    Log.d(clazz.simpleName, mess)
}

fun openVideo(context: Context, path: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    val file = File(path)
    val uri = FileProvider.getUriForFile(context, "l.liubin.com.fileprovider", file)
    intent.setDataAndType(uri, "video/*")

    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    context.startActivity(intent)
}

fun getPrintSize(size: Long): String {
    // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
    var value = size.toDouble()
    if (value < 1024) {
        return value.toString() + "B"
    } else {
        value = BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).toDouble()
    }
    // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
    // 因为还没有到达要使用另一个单位的时候
    // 接下去以此类推
    if (value < 1024) {
        return value.toString() + "KB"
    } else {
        value = BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).toDouble()
    }
    if (value < 1024) {
        return value.toString() + "MB"
    } else {
        // 否则如果要以GB为单位的，先除于1024再作同样的处理
        value = BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).toDouble()
        return value.toString() + "GB"
    }
}

//UI适配
var sNoncompatDensity: Float = 0.0f
var sNoncompatScaledDensity: Float = 0.0f
fun setCustomDensity(activity: Activity, application: Application) {
    var metrics = application.resources.displayMetrics
    if (sNoncompatDensity == 0.0f) {
        sNoncompatDensity = metrics.density
        sNoncompatScaledDensity = metrics.scaledDensity
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onConfigurationChanged(newConfig: Configuration?) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    sNoncompatScaledDensity = application.resources.displayMetrics.scaledDensity
                }
            }
        })
    }
    var density: Float = (metrics.widthPixels / 360).toFloat()
    var scaleDensity = density * (sNoncompatScaledDensity / sNoncompatDensity)
    var dpi: Int = (160 * density).toInt()
    metrics.density = density
    metrics.scaledDensity = scaleDensity
    metrics.densityDpi = dpi
    var activityMetrics = activity.resources.displayMetrics
    activityMetrics.densityDpi = dpi
    activityMetrics.density = density
    activityMetrics.scaledDensity = scaleDensity

}

/**
 * 打卡软键盘
 */
fun openKeyBord(mEditText: EditText, mContext: Context) {
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * 关闭软键盘
 */
fun closeKeyBord(mEditText: EditText, mContext: Context) {
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
}

fun Double.toDoubleNumber(): String {
    var str = DecimalFormat("#.00").format(this)
    if (this < 1) {
        str = "0$str"
    }
    return str
}

abstract class AdapterListener {
    open fun onMoreShow() {}
    open fun onMoreClick() {}
    open fun onErrorShow() {}
    open fun onErrorClick() {}
}

fun initAdapter(adapter: RecyclerArrayAdapter<*>, listener: AdapterListener? = null) {
    adapter.setMore(R.layout.view_loadmore, object : RecyclerArrayAdapter.OnMoreListener {
        override fun onMoreShow() {
            listener?.onMoreShow()
        }

        override fun onMoreClick() {
            listener?.onMoreClick()
        }
    })
    adapter.setNoMore(R.layout.view_nomore)
    adapter.setError(R.layout.view_moreerror, object : RecyclerArrayAdapter.OnErrorListener {
        override fun onErrorClick() {
            listener?.onErrorClick()
        }

        override fun onErrorShow() {
            listener?.onErrorShow()
        }
    })
}

fun initRecyclerView(easyRecyclerView: EasyRecyclerView, resId: Int = R.layout.view_empty) {
    easyRecyclerView.setEmptyView(resId)
}

fun dip2px(context: Context, dpValue: Float): Int {
    var scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun px2dip(context: Context, dpValue: Float): Int {
    var scale = context.resources.displayMetrics.density
    return (dpValue / scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}