package l.liubin.com.videokotlin.download

/**
 * Created by l on 2018/5/18.
 */
abstract class DownloadListener {
    fun onStartDownload(url: String){}
    fun onProgress(progress: Long){}
    fun onFinishDownload(){}
    fun onFail(errorInfo: String){}
}