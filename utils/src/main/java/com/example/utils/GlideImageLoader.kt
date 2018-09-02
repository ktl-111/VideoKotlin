package com.example.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader
import l.liubin.com.videokotlin.utils.GlideUils

/**
 * Created by l on 2018/5/10.
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideUils.loadImg(context, path, imageView)
        var url = Uri.parse(path as String)
        imageView.setImageURI(url)
    }
}