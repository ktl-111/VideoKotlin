package com.example.categoriesmodel.mvp.model

import com.example.categoriesmodel.mvp.CatrgoriesBaseModel
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import io.reactivex.functions.Function
import l.liubin.com.videokotlin.mvp.base.BaseObserver

/**
 * Created by l on 2018/5/10.
 */
class CatrgoriesModel : CatrgoriesBaseModel() {
    fun getCatrgories(function: Function<ArrayList<CategoryBean>, ArrayList<CategoryBean>>, observer: BaseObserver<ArrayList<CategoryBean>>) {
        universal(mApiService.getCategory().map(function), observer)
    }
}