package com.tech.glstudy.world

import android.content.Context
import android.opengl.GLES30
import java.nio.FloatBuffer

/**
 *  create by Myking
 *  date : 2020/12/25 17:08
 *  description :
 */
class GLTriangel(context: Context) {
    //顶点数组
    private val vertexes = floatArrayOf( //以逆时针顺序
        0.0f, 0.0f, 0.0f,//原点
        -1.0f, 1.0f, 0.0f,
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f
    )

    // 颜色数组
    private val colors = floatArrayOf(
        1.0f, 1.0f, 1.0f, 1.0f,//白色
        1.0f, 0.0f, 0.0f, 1.0f,//红色
        0.0f, 1.0f, 0.0f, 1.0f,//绿色
        0.0f, 0.0f, 1.0f, 1.0f //蓝色
    )
    private val program: Int
    private var vertBuffer: FloatBuffer
    private var colorBuffer: FloatBuffer
    private val aPosition = 0 //位置的句柄
    private val aColor = 1 //颜色的句柄
    private var uMVPMatrix = 2 //矩阵变换的句柄

    companion object {
        private const val VERTEX_DIMENSION = 3
        private const val COLOR_DIMENSION = 4
    }

    init {
        program = GLLoader.initProgramFromAssets(context, "base.fsh", "base.vsh")
        vertBuffer = GLBuffer.getFloatBuffer(vertexes)
        colorBuffer = GLBuffer.getFloatBuffer(colors)
        uMVPMatrix = GLES30.glGetUniformLocation(program, "uMVPMatrix")
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        GLES30.glUseProgram(program)
        GLES30.glEnableVertexAttribArray(aPosition)
        GLES30.glEnableVertexAttribArray(aColor)
        //应用矩阵
        GLES30.glUniformMatrix4fv(uMVPMatrix, 1, false, mvpMatrix, 0)
        GLES30.glVertexAttribPointer(
            aPosition,
            VERTEX_DIMENSION,
            GLES30.GL_FLOAT,
            false,
            VERTEX_DIMENSION * 4,
            vertBuffer
        )

        GLES30.glVertexAttribPointer(
            aColor,
            COLOR_DIMENSION,
            GLES30.GL_FLOAT,
            false,
            COLOR_DIMENSION * 4,
            colorBuffer
        )

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vertexes.size / VERTEX_DIMENSION)

        GLES30.glDisableVertexAttribArray(aPosition)
        GLES30.glDisableVertexAttribArray(aColor)
    }
}