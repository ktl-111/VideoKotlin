package l.liubin.com.videokotlin.mvp.base

import android.os.Bundle
import l.liubin.com.videokotlin.ui.base.BaseActivity

/**
 * Created by l on 2018/5/9.
 */
abstract class MvpActivity<P : BasePresenter<*, BaseModel>> : BaseActivity() {
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.let { mPresenter.detachView() }
    }

}