package l.liubin.com.videokotlin.ui.fragment

import android.content.Intent
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_cache.*
import kotlinx.android.synthetic.main.fragment_mine.*
import l.liubin.com.videokotlin.ui.activity.CacheActivity

/**
 * Created by l on 2018/5/9.
 */
class MineFragment : BaseFragment() {
    override fun getResId(): Int = R.layout.fragment_mine

    override fun initData() {
    }

    override fun initEvent() {
        tv_mine_cache.setOnClickListener {
            startActivity(Intent(mContext,CacheActivity::class.java))
        }
    }
}