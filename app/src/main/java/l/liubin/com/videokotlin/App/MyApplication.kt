package l.liubin.com.videokotlin.App

import android.app.Application
import android.content.Context

/**
 * Created by l on 2018/5/9.
 */
class MyApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}