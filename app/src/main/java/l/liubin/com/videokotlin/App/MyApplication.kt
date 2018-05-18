package l.liubin.com.videokotlin.App

import android.app.Application
import android.content.Context
import l.liubin.com.videokotlin.bean.DaoMaster
import l.liubin.com.videokotlin.bean.DaoSession

/**
 * Created by l on 2018/5/9.
 */
class MyApplication : Application() {
    companion object {
        lateinit var context: Context
        lateinit var daoSession: DaoSession
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        setupDatabase()
    }

    private fun setupDatabase() {
        var helper = DaoMaster.DevOpenHelper(this, "download.db", null)
        //获取可写数据库
        var db = helper.writableDatabase
        //获取数据库对象
        var daoMaster = DaoMaster(db)
        //获取Dao对象管理者
        daoSession = daoMaster.newSession()
    }
}