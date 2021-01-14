package com.tech.glstudy.video

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.GLES11Ext
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import android.view.Surface
import java.io.IOException
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 *  create by Myking
 *  date : 2021/1/13 15:59
 *  description :
 */
class VideoRenderer(val context: Context, val videoUri: Uri) :
    GLSurfaceView.Renderer, MediaPlayer.OnVideoSizeChangedListener,
    SurfaceTexture.OnFrameAvailableListener {

    private var videoDrawer: VideoDrawer? = null // 绘制器
    private var videoDrawer2: VideoDrawer2? = null // 绘制器

    private val projectionMatrix = FloatArray(16)
    private val sTMatrix = FloatArray(16)

    // 视频和屏幕尺寸
    private var viewWidth = 0
    private var viewHeight = 0
    private var videoWidth = 0
    private var videoHeight = 0
    private var mediaPlayer: MediaPlayer? = null // 视频播放器
    private var surfaceTexture: SurfaceTexture? = null // 表面纹理

    @Volatile
    private var updateSurface: Boolean = false // 是否更新表面纹理

    private var textureId: Int = 0 // 纹理 id

    override fun onDrawFrame(gl: GL10?) {
        synchronized(this) {
            if (updateSurface) {
                surfaceTexture?.updateTexImage()
                surfaceTexture?.getTransformMatrix(sTMatrix)
                updateSurface = false
            }
        }
//        videoDrawer?.draw(textureId, projectionMatrix, sTMatrix)
        videoDrawer2?.draw(textureId, projectionMatrix, sTMatrix)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        viewHeight = height
        viewWidth = width
        updateProjection()
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//        videoDrawer = VideoDrawer(context)
        videoDrawer2 = VideoDrawer2(context)
        initMediaPlayer()
        mediaPlayer?.start()
    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
        videoWidth = width
        videoHeight = height
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        updateSurface = true
    }

    private fun initMediaPlayer() {
        // 创建 MediaPlayer 对象
        mediaPlayer = MediaPlayer()
        try {
            // 设置视频资源
            mediaPlayer!!.setDataSource(context, videoUri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // 设置音频流类型
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        // 设置循环播放
        mediaPlayer!!.isLooping = true
        // 设置视频尺寸变化监听器
        mediaPlayer!!.setOnVideoSizeChangedListener(this)

        // 创建 surface
        val textures = IntArray(1)
        GLES30.glGenTextures(1, textures, 0)
        textureId = textures[0]
        // 绑定纹理 id
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
        surfaceTexture = SurfaceTexture(textureId)
        surfaceTexture!!.setOnFrameAvailableListener(this)
        val surface = Surface(surfaceTexture)

        // 设置 surface
        mediaPlayer!!.setSurface(surface)
        surface.release()
        try {
            mediaPlayer!!.prepare()
        } catch (t: IOException) {
            Log.e("Prepare ERROR", "onSurfaceCreated: ")
        }
    }

    private fun updateProjection() {
        //正交投影矩阵
        Matrix.orthoM(
            projectionMatrix, 0,
            -1f, 1f, -1f, 1f,
            -1f, 1f
        )
    }
}