package l.liubin.com.videokotlin.ui.activity

import kotlinx.android.synthetic.main.activity_main.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.ui.fragment.FindFragment
import l.liubin.com.videokotlin.ui.fragment.IndexFragment
import l.liubin.com.videokotlin.ui.fragment.MineFragment
import l.liubin.com.videokotlin.ui.fragment.PopularFragment

class MainActivity : BaseActivity() {
    lateinit var mIndexFragment: IndexFragment
    lateinit var mFindFragment: FindFragment
    lateinit var mPopularFragment: PopularFragment
    lateinit var mMineFragment: MineFragment

    override fun getResId(): Int = R.layout.activity_main

    override fun initData() {

    }

    override fun initEvent() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        rg_home_layout.setOnCheckedChangeListener { _, Id ->
            when (Id) {
                R.id.rb_home_index -> {
                    mIndexFragment?.let { beginTransaction.show(it) }
                            ?: IndexFragment()?.let {
                        mIndexFragment = it
                        beginTransaction.add(R.id.fl_home_content, it, "home")
                    }
                }
                R.id.rb_home_find->{
                    mFindFragment?.let { beginTransaction.show(it) }
                    ?:FindFragment()?.let { mFindFragment = it
                    beginTransaction.add(R.id.fl_home_content,it,"find")}
                }
                R.id.rb_home_popular->{
                    mPopularFragment?.let { beginTransaction.show(it) }
                    ?:PopularFragment()?.let { mPopularFragment = it
                    beginTransaction.add(R.id.fl_home_content,it,"popular")}
                }
                R.id.rb_home_mine->{
                    mMineFragment?.let { beginTransaction.show(mMineFragment) }
                    ?:MineFragment()?.let { mMineFragment = it
                    beginTransaction.add(R.id.fl_home_content,it,"mine")}
                }
            }
            beginTransaction.commit()
        }
    }
}
