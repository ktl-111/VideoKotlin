package l.liubin.com.videokotlin.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cache.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.datebase.DownloadModel
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.viewholder.CacheViewHolder

/**
 * Created by l on 2018/5/21.
 */
class CacheActivity : BaseActivity() {
    lateinit var mAdapter: RecyclerArrayAdapter<DownloadModel>
    override fun getResId(): Int = R.layout.activity_cache
    override fun initData() {
        erv_cache_list.setLayoutManager(LinearLayoutManager(mContext))
        mAdapter = object : RecyclerArrayAdapter<DownloadModel>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
                return CacheViewHolder(parent, mContext)
            }

        }
        erv_cache_list.adapter = mAdapter
        Observable.create(ObservableOnSubscribe<List<DownloadModel>> { e ->
            var list = Select().from(DownloadModel::class.java).queryList()
            e.onNext(list)
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    mAdapter.clear()
                    mAdapter.addAll(list)
                }
    }

    override fun initEvent() {
    }
}