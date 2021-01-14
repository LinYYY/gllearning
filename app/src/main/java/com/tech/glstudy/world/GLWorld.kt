package com.tech.glstudy.world

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 *  create by Myking
 *  date : 2020/12/24 17:22
 *  description :
 */
class GLWorld @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    GLSurfaceView(context, attributeSet), GLSurfaceView.Renderer {

    //    private lateinit var glPoint: GLPoint
//    private lateinit var glTriangel: GLTriangel
    private lateinit var glPicture: GLPicture

    //Model View Projection Matrix--模型视图投影矩阵
    private val mMVPMatrix: FloatArray = FloatArray(16)

    //投影矩阵 mProjectionMatrix
    private val mProjectionMatrix: FloatArray = FloatArray(16)

    //视图矩阵 mViewMatrix
    private val mViewMatrix: FloatArray = FloatArray(16)


    init {
        setEGLContextClientVersion(3)
        setRenderer(this)
    }

    override fun onDrawFrame(gl: GL10?) {
        Matrix.multiplyMM(
            mMVPMatrix, 0,
            mProjectionMatrix, 0,
            mViewMatrix, 0
        )
        glPicture.draw(mMVPMatrix)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
        val radio = width / (height * 1f)
        //透视投影矩阵--截锥
        Matrix.frustumM(mProjectionMatrix, 0, -radio, radio, -1f, 1f, 3f, 7f)
        // 设置相机位置(视图矩阵)
        Matrix.setLookAtM(
            mViewMatrix, 0,
            0f, 0f, 4f,
            0f, 0f, 0f,
            0f, 1.0f, 0.0f
        )

    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//        GLES30.glClearColor(0f, 0f, 0f, 1f)
        glPicture = GLPicture(context)
    }
}