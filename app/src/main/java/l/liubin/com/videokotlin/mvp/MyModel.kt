package l.liubin.com.videokotlin.mvp

import l.liubin.com.videokotlin.api.MyApi
import l.liubin.com.videokotlin.mvp.base.BaseModel

/**
 * Created by steam_l on 2018/8/31.
 * Desprition :中间件m,为了获取当前model对应的api类
 */
open class MyModel : BaseModel<MyApi>() {
    override fun getClazz(): Class<MyApi> = apiClazz

    companion object {
        val apiClazz = MyApi::class.java
    }
}