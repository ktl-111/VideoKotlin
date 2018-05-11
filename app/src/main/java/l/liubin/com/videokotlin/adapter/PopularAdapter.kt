package l.liubin.com.videokotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import l.liubin.com.videokotlin.ui.fragment.HotListFragment

/**
 * Created by l on 2018/5/11.
 */
class PopularAdapter(manager: FragmentManager, urls: ArrayList<String>) : FragmentPagerAdapter(manager) {
    var fragments = arrayListOf<HotListFragment>()

    init {
        urls.forEach { fragments.add(HotListFragment.getInstance(it)) }
    }

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}