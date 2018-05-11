package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/11.
 */
class CatroiesDetailsModel : BaseModel() {
    fun getDetailsList(id: Long, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getCategoryDetailList(id), baseObserver)
    }

    fun getMore(url: String, baseObserver: BaseObserver<HomeBean.Issue>) {
        universal(mApiService.getIssueData(url), baseObserver)
    }

}