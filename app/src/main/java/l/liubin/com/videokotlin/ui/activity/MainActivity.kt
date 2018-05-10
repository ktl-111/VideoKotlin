package l.liubin.com.videokotlin.ui.activity

import kotlinx.android.synthetic.main.activity_main.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.ui.base.BaseFragment
import l.liubin.com.videokotlin.ui.fragment.FindFragment
import l.liubin.com.videokotlin.ui.fragment.IndexFragment
import l.liubin.com.videokotlin.ui.fragment.MineFragment
import l.liubin.com.videokotlin.ui.fragment.PopularFragment

class MainActivity : BaseActivity() {
    var mIndexFragment: IndexFragment? = null
    var mFindFragment: FindFragment? = null
    var mPopularFragment: PopularFragment? = null
    var mMineFragment: MineFragment? = null
    var mCurrFragment: BaseFragment? = null

    override fun getResId(): Int = R.layout.activity_main

    override fun initData() {

    }

    override fun initEvent() {
        rg_home_layout.setOnCheckedChangeListener { _, Id ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            mCurrFragment?.let { beginTransaction.hide(it) }
            when (Id) {
                R.id.rb_home_index -> {
                    mIndexFragment?.let { beginTransaction.show(it) }
                            ?: IndexFragment()?.let {
                        mIndexFragment = it
                        beginTransaction.add(R.id.fl_home_content, it, "home")
                    }
                    mCurrFragment = mIndexFragment
                    immersionBar.fitsSystemWindows(false).transparentStatusBar().init()
                }
                R.id.rb_home_find -> {
                    mFindFragment?.let { beginTransaction.show(it) }
                            ?: FindFragment()?.let {
                        mFindFragment = it
                        beginTransaction.add(R.id.fl_home_content, it, "find")
                    }
                    mCurrFragment = mIndexFragment
                    immersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).init()
                }
                R.id.rb_home_popular -> {
                    mPopularFragment?.let { beginTransaction.show(it) }
                            ?: PopularFragment()?.let {
                        mPopularFragment = it
                        beginTransaction.add(R.id.fl_home_content, it, "popular")
                    }
                    mCurrFragment = mIndexFragment
                    immersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).init()
                }
                R.id.rb_home_mine -> {
                    mMineFragment?.let { beginTransaction.show(mMineFragment) }
                            ?: MineFragment()?.let {
                        mMineFragment = it
                        beginTransaction.add(R.id.fl_home_content, it, "mine")
                    }
                    mCurrFragment = mIndexFragment
                    immersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).init()
                }
            }
            beginTransaction.commitAllowingStateLoss()
        }
        rg_home_layout.check(R.id.rb_home_index)
    }
}
