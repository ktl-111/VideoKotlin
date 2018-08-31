package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import l.liubin.com.videokotlin.mvp.MyModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/10.
 */
class PopularModel: MyModel()  {
    fun getPopular(observer: BaseObserver<TabInfoBean>){
        universal(mApiService.getRankList(),observer)
    }
}