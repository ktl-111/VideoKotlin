package l.liubin.com.videokotlin.databases

import l.liubin.com.videokotlin.App.MyApplication
import l.liubin.com.videokotlin.bean.DownloadBean
import l.liubin.com.videokotlin.bean.DownloadBeanDao

/**
 * Created by l on 2018/5/18.
 */
object DownloadDao {
    fun getDao(): DownloadBeanDao {
        return MyApplication.daoSession.downloadBeanDao
    }

    fun insert(downloadBean: DownloadBean) {
        getDao().insert(downloadBean)
    }

    fun delete(id: Long) {
        getDao().deleteByKey(id)
    }

    fun update(downloadBean: DownloadBean) {
        getDao().update(downloadBean)
    }

    fun query(): List<DownloadBean> {
        return getDao().loadAll()
    }

}