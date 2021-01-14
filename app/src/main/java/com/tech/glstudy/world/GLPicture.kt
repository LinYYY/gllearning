package com.tech.glstudy.world

import android.content.Context
import android.opengl.GLES30
import com.tech.glstudy.R
import java.nio.FloatBuffer

/**
 *  create by Myking
 *  date : 2020/12/28 16:14
 *  description :
 */
class GLPicture(context: Context) {
    companion object {
        private const val VERTEX_DIMENSION = 3
        private const val TEXTURE_DIMENSION = 2
    }

    //顶点数组
    private val vertexes = floatArrayOf( //以逆时针顺序
        1.0f, 1.0f, 0.0f,//原点
        -1.0f, 1.0f, 0.0f,
        -1.0f, -1.0f, 0.0f,
        1.0f, -1.0f, 0.0f
    )

    private val textureCoo = floatArrayOf(
        1.0f, 0.0f,
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f
    )


    private val program: Int
    private var vertexBuffer: FloatBuffer
    private var textureBuffer: FloatBuffer

    private val aPosition = 0//位置句柄
    private val aTextureCoo = 1//颜色句柄
    private val aTextureCoo2 = 2//颜色句柄2
    private var uMVPMatrix = 0//顶点变换矩阵
    private var textureId = 0//纹理ID
    private var textureId2 = 1//纹理ID2
    private var uTexture = 0
    private var uTexture2 = 0
    private var uThreshold = 0//黑白阈值句柄

    init {
        program = GLLoader.initProgramFromAssets(context, "lattice.fsh", "texture.vsh")
        vertexBuffer = GLBuffer.getFloatBuffer(vertexes)
        textureBuffer = GLBuffer.getFloatBuffer(textureCoo)
        textureId = GLTexture.loadTexture(context, R.drawable.pic_beauty)
//        textureId2 = GLTexture.loadTexture(context, R.drawable.beauty2)
        uMVPMatrix = GLES30.glGetUniformLocation(program, "uMVPMatrix")
        uTexture = GLES30.glGetUniformLocation(program, "uTexture")
//        uTexture2 = GLES30.glGetUniformLocation(program, "uTexture2")
//        uThreshold = GLES30.glGetUniformLocation(program, "uThreshold")
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        GLES30.glUseProgram(program)
        GLES30.glUniformMatrix4fv(uMVPMatrix, 1, false, mvpMatrix, 0)
//        GLES30.glUniform1f(uThreshold, 0.7f)

        GLES30.glEnableVertexAttribArray(aPosition)
        GLES30.glEnableVertexAttribArray(aTextureCoo)

        GLES30.glVertexAttribPointer(
            aPosition,
            VERTEX_DIMENSION,
            GLES30.GL_FLOAT,
            false,
            VERTEX_DIMENSION * 4,
            vertexBuffer
        )

        GLES30.glVertexAttribPointer(
            aTextureCoo, TEXTURE_DIMENSION, GLES30.GL_FLOAT, false,
            TEXTURE_DIMENSION * 4, textureBuffer
        )

        //启用纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        //绑定纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId)
//        GLES30.glUniform1f(uTexture,0f)

//        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
//        //绑定纹理
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId2)
//        GLES30.glUniform1f(uTexture2,1f)

        GLES30.glLineWidth(10f)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, vertexes.size / VERTEX_DIMENSION)

        GLES30.glDisableVertexAttribArray(aPosition)
        GLES30.glDisableVertexAttribArray(aTextureCoo)
    }
}