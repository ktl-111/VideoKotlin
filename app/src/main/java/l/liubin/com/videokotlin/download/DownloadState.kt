package l.liubin.com.videokotlin.download

/**
 * Created by l on 2018/5/18.
 */
object DownloadState {
    val STATE_START = 1.shl(1)//开始
    val STATE_PAUSE = 1.shl(2)//停止
    val STATE_SUCCESS = 1.shl(3)//下载完成
    val STATE_FAILED = 1.shl(4)//失败
}