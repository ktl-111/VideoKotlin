package l.liubin.com.videokotlin.mvp.base

import android.os.Bundle
import l.liubin.com.videokotlin.ui.base.BaseFragment

/**
 * Created by l on 2018/5/9.
 */
abstract class MvpFragment<P : BasePresenter<*, BaseModel>> : BaseFragment() {
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresent()
    }

    abstract fun createPresent(): P
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.let { mPresenter.detachView() }
    }
}