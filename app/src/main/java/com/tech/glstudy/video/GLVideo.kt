package com.tech.glstudy.video

import android.content.Context
import android.net.Uri
import android.opengl.GLSurfaceView
import com.tech.glstudy.R

/**
 *  create by Myking
 *  date : 2021/1/13 15:57
 *  description :
 */
class GLVideo(context: Context) : GLSurfaceView(context) {

    private val renderer: VideoRenderer

    init {
        setEGLContextClientVersion(3)
        val video = Uri.parse("android.resource://${context.packageName}/${R.raw.test}")
        renderer = VideoRenderer(context, video)
        setRenderer(renderer)
    }
}