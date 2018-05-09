package l.liubin.com.videokotlin.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by l on 2018/5/9.
 */
abstract class BaseFragment : Fragment() {
    var mRootView: View? = null
    lateinit var mContext: Context
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater?.inflate(getResId(), container, false)
        mContext = activity
        return mRootView
    }

    abstract fun getResId(): Int
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataBefore()
        initData()
        initEvent()
    }

    abstract fun initDataBefore()
    abstract fun initData()
    abstract fun initEvent()
}