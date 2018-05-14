package l.liubin.com.videokotlin.orther

import android.content.Context
import android.support.v7.widget.RecyclerView
import l.liubin.com.videokotlin.utils.dip2px

/**
 * Created by l on 2018/5/14.
 */
class FlowManager(context: Context) : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = RecyclerView.LayoutParams(-1, -2)
    val marginsLeft = dip2px(context, 10.0f)
    val marginsTop = dip2px(context, 10.0f)
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0 || state.isPreLayout) {
            return
        }
        detachAndScrapAttachedViews(recycler)
        var offsetY = paddingTop + marginsTop
        var totalWidth = 0
        for (i in 0 until itemCount) {
            var view = recycler.getViewForPosition(i)
            measureChildWithMargins(view, 0, 0)
            var width = getDecoratedMeasuredWidth(view)
            var height = getDecoratedMeasuredHeight(view)
            if (totalWidth + width + marginsLeft >= getHorizontalSpace()) {
                totalWidth = 0
                offsetY += height + marginsTop
            }
            if (totalWidth != 0) {
                totalWidth += marginsLeft
            }
            addView(view)

            layoutDecorated(view, totalWidth, offsetY, width + totalWidth, height + offsetY)
            totalWidth += width
        }
    }

    fun getHorizontalSpace(): Int = width - paddingLeft - paddingRight
}