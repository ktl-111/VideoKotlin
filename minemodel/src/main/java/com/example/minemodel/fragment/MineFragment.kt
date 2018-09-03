package com.example.minemodel.fragment

import android.content.Intent
import com.example.minemodel.R
import com.example.minemodel.activity.CacheActivity
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created by l on 2018/5/9.
 */
class MineFragment : l.liubin.com.videokotlin.ui.base.BaseFragment() {
    override fun getResId(): Int = R.layout.fragment_mine

    override fun initData() {
    }

    override fun initEvent() {
        tv_mine_cache.setOnClickListener {
            startActivity(Intent(mContext, CacheActivity::class.java))
        }
    }
}