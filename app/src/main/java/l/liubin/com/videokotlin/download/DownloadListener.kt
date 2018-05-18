package l.liubin.com.videokotlin.download

/**
 * Created by l on 2018/5/18.
 */
interface DownloadListener {
    fun onStartDownload(url: String)
    fun onProgress(progress: Int)
    fun onFinishDownload()
    fun onFail(errorInfo: String)
}