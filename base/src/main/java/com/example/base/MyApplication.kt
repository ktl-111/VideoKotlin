package com.example.base

import android.app.Application
import android.content.Context
import com.example.utils.BlockDetectByChoreographer
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager

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
        FlowManager.init(FlowConfig.builder(context)
                .build())
        BlockDetectByChoreographer.start()
    }

}