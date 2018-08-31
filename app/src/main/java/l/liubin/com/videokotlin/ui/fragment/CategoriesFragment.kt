package l.liubin.com.videokotlin.ui.fragment

import android.content.Intent
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import com.example.base.MyApplication
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.jude.easyrecyclerview.decoration.SpaceDecoration
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.include_title.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.mvp.base.MvpFragment
import l.liubin.com.videokotlin.mvp.presenter.CatrgoriesPresenter
import l.liubin.com.videokotlin.mvp.view.CatrgoriesView
import l.liubin.com.videokotlin.ui.activity.CatrgoriesDetailsActivity
import l.liubin.com.videokotlin.utils.SingToast
import l.liubin.com.videokotlin.utils.Utils
import l.liubin.com.videokotlin.utils.dip2px
import l.liubin.com.videokotlin.viewholder.CatrgoriesViewHolder

/**
 * Created by l on 2018/5/9.
 */
class CategoriesFragment : MvpFragment<CatrgoriesPresenter>(), CatrgoriesView {

    override fun getResId(): Int = R.layout.fragment_categories
    lateinit var mAdapter: RecyclerArrayAdapter<CategoryBean>

    override fun initData() {
        tv_include_title.text = Utils.getStringFromResources(MyApplication.context,R.string.categories)
        mAdapter = object : RecyclerArrayAdapter<CategoryBean>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return CatrgoriesViewHolder(parent, mContext)
            }
        }
        erv_categories_list.setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
        var decoration = SpaceDecoration(dip2px(mContext, 10f))
        erv_categories_list.addItemDecoration(decoration)
        erv_categories_list.adapter = mAdapter
        mPresenter.getCatrgories(mContext)
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = mAdapter.getItem(position)
            startActivity(Intent(mContext, CatrgoriesDetailsActivity::class.java).putExtra(CatrgoriesDetailsActivity.INTENT_BEAN, item))
        }
    }

    override fun createPresent(): CatrgoriesPresenter = CatrgoriesPresenter(this)

    override fun onSuccess(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun onError(msg: String) {
        SingToast.showToast(mContext, msg)
    }

    override fun showLoading() {
    }

    override fun hindeLoading() {
    }

    override fun showList(list: ArrayList<CategoryBean>) {
        mAdapter.clear()
        mAdapter.addAll(list)
    }
}