package l.liubin.com.videokotlin.download

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * Created by l on 2018/5/18.
 */
class DownloadResponseBody(var responseBody: ResponseBody, var downloadListener: DownloadListener? = null) : ResponseBody() {
    var bufferedSource: BufferedSource? = null
    override fun contentLength(): Long = responseBody.contentLength()

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun source(): BufferedSource {
        bufferedSource ?: Okio.buffer(source(responseBody.source()))?.also { bufferedSource = it }
        return bufferedSource!!
    }

    fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead: Long = 0

            override fun read(sink: Buffer, byteCount: Long): Long {
                var bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != (-1).toLong()) {
                    bytesRead
                } else {
                    0
                }
                downloadListener?.apply {
                    if (bytesRead != (-1).toLong()) {
                        onProgress((totalBytesRead * 100 / responseBody.contentLength()).toInt())
                    }
                }
                return bytesRead
            }
        }
    }
}