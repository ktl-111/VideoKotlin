package l.liubin.com.videokotlin

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("l.liubin.com.videokotlin", appContext.packageName)
    }

    @Test
    fun testSize() {

//        var url = "http://shouji.360tpcdn.com/170918/93d1695d87df5a0c0002058afc0361f1/com.ss.android.article.news_636.apk"
//        var downloadServer = ApiEngine.apiEngine.getApiService()
//        Observable.just("")
//                .subscribeOn(Schedulers.io())
//                .map {
//                    var request = Request.Builder().url(url).method("HEAD", null).build()
//                    var response = ApiEngine.getOkHttpBuidler().build().newCall(request).execute()
//                    println("${response.code()}----${HttpHeaders.contentLength(response.headers())}---------")
//                    "完成"
//                }.subscribe(object : Observer<String> {
//                    override fun onComplete() {
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onNext(t: String) {
//                        println(t)
//                    }
//
//                    override fun onError(e: Throwable) {
//                    }
//
//                })
    }
}
