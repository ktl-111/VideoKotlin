package l.liubin.com.videokotlin.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseModel
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/15.
 */
class VideoModel:BaseModel() {
    fun getOrtherData(id:Long,baseObserver: BaseObserver<HomeBean.Issue>){
        universal(mApiService.getRelatedData(id),baseObserver)
    }
}