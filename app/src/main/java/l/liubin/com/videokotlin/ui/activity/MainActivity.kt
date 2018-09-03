package l.liubin.com.videokotlin.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.RadioButton
import com.example.categoriesmodel.fragment.CategoriesFragment
import com.example.indexmodel.fragment.IndexFragment
import com.example.minemodel.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.ui.base.BaseFragment
import l.liubin.com.videokotlin.ui.fragment.PopularFragment

class MainActivity : BaseActivity() {
    private var mIndexFragment: IndexFragment? = null
    private var mFindFragment: CategoriesFragment? = null
    private var mPopularFragment: PopularFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mCurrFragment: BaseFragment? = null
    val BUNDLE_ID = "bundle_id"

    val TAG_INDEX = "home"
    val TAG_FIND = "find"
    val TAG_POPULAR = "popular"
    val TAG_MINE = "mine"
    override fun getResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.apply {
            supportFragmentManager.findFragmentByTag(TAG_INDEX)?.also { mIndexFragment = it as IndexFragment }
            supportFragmentManager.findFragmentByTag(TAG_FIND)?.also { mFindFragment = it as CategoriesFragment }
            supportFragmentManager.findFragmentByTag(TAG_POPULAR)?.also { mPopularFragment = it as PopularFragment }
            supportFragmentManager.findFragmentByTag(TAG_MINE)?.also { mMineFragment = it as MineFragment }
            rg_home_layout.check(getInt(BUNDLE_ID))
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        var id = 0
        for (i in 0 until rg_home_layout.childCount) {
            var item = rg_home_layout.getChildAt(i) as RadioButton
            if (item.isChecked) {
                id = i
            }
        }
        outState.putInt(BUNDLE_ID, id)
    }

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
                                beginTransaction.add(R.id.fl_home_content, it, TAG_INDEX)
                            }
                    mCurrFragment = mIndexFragment
                    immersionBar.fitsSystemWindows(false).transparentStatusBar().init()
                }
                R.id.rb_home_find -> {
                    mFindFragment?.let { beginTransaction.show(it) }
                            ?: CategoriesFragment()?.let {
                                mFindFragment = it
                                beginTransaction.add(R.id.fl_home_content, it, TAG_FIND)
                            }
                    mCurrFragment = mFindFragment
                    immersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).init()
                }
                R.id.rb_home_popular -> {
                    mPopularFragment?.let { beginTransaction.show(it) }
                            ?: PopularFragment()?.let {
                                mPopularFragment = it
                                beginTransaction.add(R.id.fl_home_content, it, TAG_POPULAR)
                            }
                    mCurrFragment = mPopularFragment
                    immersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).init()
                }
                R.id.rb_home_mine -> {
                    mMineFragment?.let { beginTransaction.show(it) }
                            ?: MineFragment()?.let {
                                mMineFragment = it
                                beginTransaction.add(R.id.fl_home_content, it, TAG_MINE)
                            }
                    mCurrFragment = mMineFragment
                    immersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).init()
                }
            }
            beginTransaction.commitAllowingStateLoss()
        }
        rg_home_layout.check(R.id.rb_home_index)
    }
}
