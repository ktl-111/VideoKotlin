package l.liubin.com.videokotlin.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.VideoModel
import l.liubin.com.videokotlin.mvp.view.VideoView

/**
 * Created by l on 2018/5/15.
 */
class VideoPresenter(mView: VideoView) : BasePresenter<VideoView, VideoModel>(mView) {
    fun getOrtherData(id: Long) {
        mModel.getOrtherData(id, object : BaseObserver<HomeBean.Issue>(this@VideoPresenter, mView!!) {
            override fun onSuccess(data: HomeBean.Issue) {
                mView?.apply { showOrtherView(data.itemList) }
            }
        })
    }

    override fun createModel(): VideoModel = VideoModel()
}