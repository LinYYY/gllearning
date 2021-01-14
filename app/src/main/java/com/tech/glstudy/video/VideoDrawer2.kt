package com.tech.glstudy.video

import android.content.Context
import android.opengl.GLES11Ext
import android.opengl.GLES30
import com.tech.glstudy.world.GLBuffer
import com.tech.glstudy.world.GLLoader
import java.nio.FloatBuffer

/**
 *  create by Myking
 *  date : 2021/1/13 17:54
 *  description :
 */
class VideoDrawer2(context: Context) {
    private val vertexBuffer: FloatBuffer
    private val textureVertexBuffer: FloatBuffer
    private val vertexData = floatArrayOf(
        1f, -1f, 0f,
        -1f, -1f, 0f,
        1f, 1f, 0f,
        -1f, 1f, 0f
    )
    private val textureVertexData = floatArrayOf(
        1f, 0f,
        0f, 0f,
        1f, 1f,
        0f, 1f
    )
    private val aPositionLocation = 0
    private val aTextureCoordLocation = 1
    private val uMatrixLocation = 2
    private val uSTMMatrixLocation = 3
    private val uSTextureLocation = 4
    private val programId: Int
    private val uProgressLocation = 5
    private var progress = 0.0f

    fun draw(
        textureId: Int,
        projectionMatrix: FloatArray,
        sTMatrix: FloatArray
    ) {
        progress += 0.02f
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
        GLES30.glUseProgram(programId)
        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)
        GLES30.glUniformMatrix4fv(uSTMMatrixLocation, 1, false, sTMatrix, 0)
        GLES30.glUniform1f(uProgressLocation, progress)
        GLES30.glEnableVertexAttribArray(aPositionLocation)
        GLES30.glVertexAttribPointer(aPositionLocation, 3, GLES30.GL_FLOAT, false, 12, vertexBuffer)
        GLES30.glEnableVertexAttribArray(aTextureCoordLocation)
        GLES30.glVertexAttribPointer(
            aTextureCoordLocation,
            2,
            GLES30.GL_FLOAT,
            false,
            8,
            textureVertexBuffer
        )
        GLES30.glTexParameterf(
            GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
            GLES30.GL_TEXTURE_MAG_FILTER,
            GLES30.GL_LINEAR.toFloat()
        )
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
        GLES30.glUniform1i(uSTextureLocation, 0)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4)
    }

    init {
        vertexBuffer = GLBuffer.getFloatBuffer(vertexData)
        textureVertexBuffer = GLBuffer.getFloatBuffer(textureVertexData)
        programId = GLLoader.initProgramFromAssets(context, "video_ci.fsh", "video.vsh")
    }
}