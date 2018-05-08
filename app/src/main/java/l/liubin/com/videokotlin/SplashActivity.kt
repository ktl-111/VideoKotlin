package l.liubin.com.videokotlin

import l.liubin.com.videokotlin.ui.base.BaseActivity

/**
 * Created by l on 2018/5/8.
 */
class SplashActivity : BaseActivity() {
    override fun getResId(): Int = R.layout.activity_splash

    init {
        immersionBar
                .fullScreen(true)
                .init()
    }

    override fun initData() {
        
    }

    override fun initEvent() {
    }

}