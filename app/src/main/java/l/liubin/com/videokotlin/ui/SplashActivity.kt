package l.liubin.com.videokotlin.ui

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_splash.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.utils.AppUtils
import kotlin.properties.Delegates

/**
 * Created by l on 2018/5/8.
 */
class SplashActivity : BaseActivity() {
    override fun getResId(): Int = R.layout.activity_splash
    var splashAnimation: AlphaAnimation by Delegates.notNull()

    init {
        immersionBar
                .fullScreen(true)
                .init()
    }

    override fun initData() {
        tv_version_name.text = "${AppUtils.getVerCode(mContext)}"
        splashAnimation = AlphaAnimation(1.0f, 0.5f)
        splashAnimation.duration = 1000
        splashAnimation.interpolator = LinearInterpolator()
        splashAnimation.fillAfter = true
        splashAnimation.startOffset = 500
        startAnimation()
    }

    override fun initEvent() {
        splashAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                startActivity(Intent(mContext, MainActivity::class.java))
                finish()
            }

        })
    }

    fun startAnimation() {
        layout_splash.startAnimation(splashAnimation)
    }

}