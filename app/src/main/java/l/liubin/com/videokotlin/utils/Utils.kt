package l.liubin.com.videokotlin.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import l.liubin.com.videokotlin.App.MyApplication
import l.liubin.com.videokotlin.R

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

abstract class AdapterListener {
    open fun onMoreShow() {}
    open fun onMoreClick() {}
    open fun onErrorShow() {}
    open fun onErrorClick() {}
}

fun initAdapter(adapter: RecyclerArrayAdapter<*>, listener: AdapterListener) {
    adapter.setMore(R.layout.view_loadmore, object : RecyclerArrayAdapter.OnMoreListener {
        override fun onMoreShow() {
            listener.onMoreShow()
        }

        override fun onMoreClick() {
            listener.onMoreClick()
        }
    })
    adapter.setNoMore(R.layout.view_nomore)
    adapter.setError(R.layout.view_moreerror, object : RecyclerArrayAdapter.OnErrorListener {
        override fun onErrorClick() {
            listener.onErrorClick()
        }

        override fun onErrorShow() {
            listener.onErrorShow()
        }
    })
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