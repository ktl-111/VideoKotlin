package l.liubin.com.videokotlin.ui.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import kotlin.properties.Delegates

/**
 * Created by l on 2018/5/8.
 */
abstract class BaseActivity : AppCompatActivity() {
    val mContext: Context by lazy { this }
    var immersionBar: ImmersionBar by Delegates.notNull()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(getResId())
        immersionBar = ImmersionBar.with(this)
        immersionBar.fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .init()
        initDataBefore()
        initData()
        initEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar?.let { it.destroy() }
    }

    abstract fun getResId(): Int

    fun initDataBefore() {
    }

    abstract fun initData()

    abstract fun initEvent()
}