package com.tech.glstudy.hello

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 *  create by Myking
 *  date : 2020/12/11 14:16
 *  description :
 */
class GLView1 @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    GLSurfaceView(
        context, attributeSet
    ) {

    init {
        setEGLContextClientVersion(2)
        val renderer = GLRender()
        setRenderer(renderer)
    }


}
