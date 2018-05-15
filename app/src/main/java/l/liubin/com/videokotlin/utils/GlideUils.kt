package l.liubin.com.videokotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import l.liubin.com.videokotlin.R

/**
 * Created by l on 2018/5/10.
 */
object GlideUils {
    fun loadImg(context: Context, url: Any, img: ImageView) {
        var options = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_loading)
                .centerCrop()
                .error(R.mipmap.pgc_default_avatar)

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(img)
    }
}