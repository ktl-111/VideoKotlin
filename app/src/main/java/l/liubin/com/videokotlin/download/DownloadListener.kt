package l.liubin.com.videokotlin.download

import l.liubin.com.videokotlin.datebase.DownloadModel

/**
 * Created by l on 2018/5/18.
 */
abstract class DownloadListener {
    open fun onStartDownload(model: DownloadModel) {}
    open fun onProgress(model: DownloadModel) {}
    open fun onPause(model: DownloadModel) {}
    open fun onFinishDownload(model: DownloadModel) {}
    open fun onFiled(model: DownloadModel) {}
    open fun onWait(model: DownloadModel) {}
    open fun onStop(model:DownloadModel){}
}