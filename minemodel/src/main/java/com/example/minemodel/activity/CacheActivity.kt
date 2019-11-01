package com.example.minemodel.activity

import android.os.Environment
import android.support.v7.app.AlertDialog
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.ViewGroup
import com.example.downloadmodel.datebase.DownloadModel
import com.example.downloadmodel.datebase.DownloadModel_Table
import com.example.minemodel.R
import com.example.minemodel.viewholder.CacheViewHolder
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_cache.*
import kotlinx.android.synthetic.main.include_title.*
import l.liubin.com.videokotlin.ui.base.BaseActivity
import java.io.File

/**
 * Created by l on 2018/5/21.
 */
class CacheActivity : BaseActivity() {
    lateinit var mAdapter: com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter<DownloadModel>
    override fun getResId(): Int = R.layout.activity_cache
    override fun initData() {
        var str = SpannableString("${l.liubin.com.videokotlin.utils.Utils.getStringFromResources(com.example.base.MyApplication.Companion.context, R.string.my_cache)}(长按删除)")

        str.setSpan(RelativeSizeSpan(0.7f), l.liubin.com.videokotlin.utils.Utils.getStringFromResources(com.example.base.MyApplication.Companion.context, R.string.my_cache).length, str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_include_title.text = str
        erv_cache_list.setLayoutManager(android.support.v7.widget.LinearLayoutManager(mContext))
        mAdapter = object : com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter<DownloadModel>(mContext) {
            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): com.jude.easyrecyclerview.adapter.BaseViewHolder<*> {
                return CacheViewHolder(parent, mContext)
            }
        }
        l.liubin.com.videokotlin.utils.initRecyclerView(erv_cache_list)
        erv_cache_list.adapter = mAdapter
        io.reactivex.Observable.create(ObservableOnSubscribe<List<DownloadModel>> { e ->
            var list = Select().from(DownloadModel::class.java).queryList()
            e.onNext(list)
        })
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe { list ->
                    if (list.isEmpty()) {
                        erv_cache_list.showEmpty()
                    } else {
                        mAdapter.clear()
                        mAdapter.addAll(list)
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        com.example.downloadmodel.manager.DownloadManager.Companion.getInstance(mContext).clearListener()
    }

    fun addDownload(v: View) {
        createDownload("http://shouji.360tpcdn.com/170901/ec1eaad9d0108b30d8bd602da9954bb7/com.taobao.taobao_161.apk"
                , "淘宝", "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=961224733,2320658306&fm=179&app=42&f=JPEG?w=121&h=140")
        createDownload("http://shouji.360tpcdn.com/170918/a01da193400dd5ffd42811db28effd53/com.tencent.mobileqq_730.apk"
                , "qq", "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2958518472,3404372203&fm=179&app=42&f=JPEG?w=121&h=140")
        createDownload("http://shouji.360tpcdn.com/170919/9f1c0f93a445d7d788519f38fdb3de77/com.UCMobile_704.apk"
                , "UC", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3351548615,2238055624&fm=58&s=FDA63D7289A57803456855EF0200902B&bpow=121&bpoh=75")
        createDownload("http://shouji.360tpcdn.com/170922/9ffde35adefc28d3740d4e16612f078a/com.tencent.tmgp.sgame_22011304.apk"
                , "王者荣耀", "http://p5.qhimg.com/dr/72__/t01a362a049573708ae.png")
        createDownload("http://shouji.360tpcdn.com/170919/04fd8ec516c87571bf274537419b7651/com.tencent.news_5440.apk"
                , "新闻", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1598000148,3084276147&fm=58&s=36F6EC36C8A47E92227DC7C502007026")
        createDownload("http://shouji.360tpcdn.com/170824/dfa7accaf99f4264c3f1a57c72b6b2de/com.kugou.android_8862.apk"
                , "酷狗", "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3420249703,2075615254&fm=58&s=A072CC3280259111015384CD030030AA&bpow=121&bpoh=75")
        createDownload("http://shouji.360tpcdn.com/170917/af50b75c9980cd6cba079052f4aa4e63/com.jingdong.app.mall_52563.apk"
                , "京东", "https://ss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/299c55e31d7f50ae4dc85faa90d6f445_121_121.jpg")
        createDownload("http://shouji.360tpcdn.com/170918/f7aa8587561e4031553316ada312ab38/com.tencent.qqlive_13049.apk"
                , "QQ空间", "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2562868045,1335529899&fm=58&s=7BA63D66C5247D011475CCC400003033&bpow=121&bpoh=75")
        createDownload("http://shouji.360tpcdn.com/170919/e7f5386759129f378731520a4c953213/com.eg.android.AlipayGphone_115.apk"
                , "支付宝", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2881502819,1702418975&fm=58&bpow=400&bpoh=400")
        createDownload("http://shouji.360tpcdn.com/170922/b9122b7a3c969511c4584fecd6e0b56a/com.autonavi.minimap_6180.apk"
                , "高德", "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2727832369,1423201121&fm=58&s=96E5FC169CF069804FE815C60300E0B2&bpow=121&bpoh=75")
        createDownload("http://shouji.360tpcdn.com/170919/1a4d1a0ca1255ae315c36394dd2b0865/com.mt.mtxx.mtxx_6860.apk"
                , "美图", "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1210344439,959016634&fm=58&s=54D67D32C4259B0346C94BEF0300A024&bpow=121&bpoh=75")
        createDownload("http://shouji.360tpcdn.com/170821/9a7f5c1ee54e3c5bc84070c21e9b5b49/com.tencent.mm_1100.apk"
                , "微信", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3088020551,4198140884&fm=96")
        createDownload("http://shouji.360tpcdn.com/170918/93d1695d87df5a0c0002058afc0361f1/com.ss.android.article.news_636.apk"
                , "头条", "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1530671589,3842391861&fm=58&s=3B77EC16CD04DD13147D90F600000035&bpow=121&bpoh=75")
        createDownload("http://shouji.360tpcdn.com/170918/93d1695d87df5a0c0002058afc0361f1/com.ss.android.article.news_636.apk"
                , "头条", "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1530671589,3842391861&fm=58&s=3B77EC16CD04DD13147D90F600000035&bpow=121&bpoh=75")
        initData()
    }

    private fun createDownload(downloadUrl: String, title: String, imgUrl: String) {
        var model = DownloadModel()
        model.download_url = downloadUrl
        model.title = title
        model.img_url = imgUrl
        model.savepath = "${Environment.getExternalStorageDirectory().path}/VideoKotlin/$title.apk"
        com.example.downloadmodel.manager.DownloadManager.getInstance(mContext).create(mContext, model)
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { position ->
            var item = com.raizlabs.android.dbflow.sql.language.Select().from(DownloadModel::class.java).where(DownloadModel_Table.download_url.`is`(mAdapter.getItem(position).download_url)).querySingle()
            item?.state?.also {
                if (it == l.liubin.com.videokotlin.download.DownloadState.STATE_SUCCESS) {
                    l.liubin.com.videokotlin.utils.openVideo(mContext, item.savepath)
                }
            }
        }
        mAdapter.setOnItemLongClickListener { position ->
            AlertDialog.Builder(mContext).setTitle("提示")
                    .setMessage("是否删除")
                    .setPositiveButton("是") { _, _ ->
                        var item = mAdapter.getItem(position)
                        item.delete()
                        var file = File(item.savepath)
                        if (file.exists()) {
                            file.delete()
                        }
                        mAdapter.remove(item)
                        if (mAdapter.allData.isEmpty()) {
                            erv_cache_list.showEmpty()
                        }
                    }
                    .setNegativeButton("否") { dialog, _ -> dialog.dismiss() }
                    .setOnCancelListener { dialog -> dialog.dismiss() }
                    .show()
            true
        }


    }
}