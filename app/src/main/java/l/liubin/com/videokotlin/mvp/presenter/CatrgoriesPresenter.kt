package l.liubin.com.videokotlin.mvp.presenter

import android.content.Context
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import io.reactivex.functions.Function
import l.liubin.com.videokotlin.mvp.base.BaseObserver
import l.liubin.com.videokotlin.mvp.base.BasePresenter
import l.liubin.com.videokotlin.mvp.model.CatrgoriesModel
import l.liubin.com.videokotlin.mvp.view.CatrgoriesView
import l.liubin.com.videokotlin.utils.dip2px
import java.util.*

/**
 * Created by l on 2018/5/10.
 */
class CatrgoriesPresenter(mView: CatrgoriesView) : BasePresenter<CatrgoriesView, CatrgoriesModel>(mView) {
    fun getCatrgories(context: Context) {
        var function = Function<ArrayList<CategoryBean>, ArrayList<CategoryBean>> { itemList ->
            itemList.forEach { it.imgHeight = Random().nextInt(dip2px(context, 40f)) + 630 }
            itemList
        }
        var observer = object : BaseObserver<ArrayList<CategoryBean>>(this@CatrgoriesPresenter, mView!!) {
            override fun onSuccess(data: ArrayList<CategoryBean>) {
                mView?.showList(data)
            }
        }
        mModel.getCatrgories(function, observer)
    }

    override fun createModel(): CatrgoriesModel = CatrgoriesModel()
}