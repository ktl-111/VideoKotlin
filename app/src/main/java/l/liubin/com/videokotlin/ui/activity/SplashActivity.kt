package l.liubin.com.videokotlin.ui.activity

//import kotlinx.android.synthetic.main.activity_splash.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseActivity

/**
 * Created by l on 2018/5/8.
 */
class SplashActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun getResId(): Int = R.layout.activity_test
//    var splashAnimation: AlphaAnimation by Delegates.notNull()
//
//    override fun initDataBefore() {
//        immersionBar
//                .fullScreen(true)
//                .init()
//    }
//
//    override fun initData() {
//        tv_version_name.text = "${AppUtils.getVerName(mContext)}"
//        splashAnimation = AlphaAnimation(1.0f, 0.5f)
//        splashAnimation.duration = 1000
//        splashAnimation.interpolator = LinearInterpolator()
//        splashAnimation.fillAfter = true
//        splashAnimation.startOffset = 500
//        startAnimation()
//    }
//
//    override fun initEvent() {
//        splashAnimation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(p0: Animation?) {
//            }
//
//            override fun onAnimationStart(p0: Animation?) {
//            }
//
//            override fun onAnimationEnd(p0: Animation?) {
//                startActivity(Intent(mContext, MainActivity::class.java))
//                finish()
//            }
//
//        })
//    }
//
//    fun startAnimation() {
//        layout_splash.startAnimation(splashAnimation)
//    }

}