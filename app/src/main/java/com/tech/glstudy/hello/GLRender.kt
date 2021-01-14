package com.tech.glstudy.hello

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *  create by Myking
 *  date : 2020/12/11 14:53
 *  description :
 */
class GLRender : GLSurfaceView.Renderer {

    companion object {
        /**
         * 加载着色器
         * @param type 顶点着色[GLES20.GL_VERTEX_SHADER] 片元着色[GLES20.GL_FRAGMENT_SHADER]
         * @param shaderCode 着色代码
         * @return 着色器
         */
        fun loadShader(type: Int, shaderCode: String): Int {
            val shader = GLES20.glCreateShader(type)//创建着色器
            GLES20.glShaderSource(shader, shaderCode)//添加着色器源代码
            GLES20.glCompileShader(shader)//编译
            return shader
        }
    }

    private lateinit var triangle: Triangle

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
        triangle.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)//创建GL视图
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(1.0f, 0f, 0f, 1f)
        triangle = Triangle()
    }
}