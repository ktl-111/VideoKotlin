package l.liubin.com.videokotlin.ui.fragment

import android.os.Bundle
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.fragment_mine.*
import l.liubin.com.videokotlin.R
import l.liubin.com.videokotlin.ui.base.BaseActivity
import l.liubin.com.videokotlin.ui.base.BaseFragment
import l.liubin.com.videokotlin.utils.Utils

/**
 * Created by l on 2018/5/9.
 */
class IndexFragment : BaseFragment() {

    override fun getResId(): Int = R.layout.fragment_index
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImmersionBar.setTitleBar(mContext as BaseActivity, tb_index_bar)
    }

    override fun initData() {
        tb_index_bar.setBackgroundColor(Utils.getColor(R.color.transparent))
    }

    override fun initEvent() {
    }
}