package l.liubin.com.videokotlin.App

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by l on 2018/5/9.
 */
class MyApplication : Application() {
    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}