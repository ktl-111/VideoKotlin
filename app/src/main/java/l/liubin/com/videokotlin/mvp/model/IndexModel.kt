package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/9.
 */
class IndexModel : BaseModel() {
    fun getIndexList(num: Int, baseObserver: BaseObserver<HomeBean>) {
        universal(mApiService.getFirstHomeData(num), baseObserver)
    }

}