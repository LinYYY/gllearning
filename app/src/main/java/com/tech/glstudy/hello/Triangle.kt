package com.tech.glstudy.hello

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *  create by Myking
 *  date : 2020/12/11 14:35
 *  description :
 */
class Triangle {

    val COORDS_PER_VERTEX = 3// 数组中每个顶点的坐标数
    val sCoo: FloatArray = floatArrayOf(//以逆时针顺序
        0.0f, 0.0f, 0.0f, // 顶部
        -1.0f, -1.0f, 0.0f, // 左下
        1.0f, -1.0f, 0.0f
    ) // 右下

    private val vertexBuffer: FloatBuffer//顶点缓冲
    private val vertexShaderCode = //顶点着色器代码
        "attribute vec4 vPosition;" +
                "void main() {" +
                "   gl_Position = vPosition;" +
                "}"
    private val fragmentShaderCode = //片源着色器代码
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "   gl_FragColor = vColor;" +
                "}"
    private val program: Int
    private var positionHandler: Int = 0//位置句柄
    private var colorHandler: Int = 0//颜色句柄
    private val vertexCount = sCoo.size / COORDS_PER_VERTEX //顶点个数
    private val vertextStride = COORDS_PER_VERTEX * 4 //3*4=12

    val colors: FloatArray = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)// 颜色，rgba

    init {
        //初始化顶点字节缓冲区
        val bb = ByteBuffer.allocateDirect(sCoo.size * 4)//每个浮点数:坐标个数* 4字节
        bb.order(ByteOrder.nativeOrder())//使用本机的硬件设备字节顺序
        vertexBuffer = bb.asFloatBuffer()// 从字节缓冲区创建浮点缓冲区
        vertexBuffer.put(sCoo)//将坐标添加到FloatBuffer
        vertexBuffer.position(0)//设置缓冲区以读取第一个坐标

        //加载顶点着色器
        val vertexShader = GLRender.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        //加载片源着色器代码
        val fragmentShader = GLRender.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        program = GLES20.glCreateProgram()//创建空的GL es程序
        GLES20.glAttachShader(program, vertexShader)//加入顶点着色器
        GLES20.glAttachShader(program, fragmentShader)//加入片源着色器
        GLES20.glLinkProgram(program) //创建可执行的GLES项目
    }

    fun draw() {
        GLES20.glUseProgram(program)//将程序添加到GLES环境中
        positionHandler = GLES20.glGetAttribLocation(program, "vPosition")//获取顶点着色器的vPosition成员的句柄
        GLES20.glEnableVertexAttribArray(positionHandler)//启用三角形的顶点句柄
        //准备三角坐标数据
        GLES20.glVertexAttribPointer(
            positionHandler,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertextStride,
            vertexBuffer
        )

        colorHandler = GLES20.glGetUniformLocation(program,"vColor")// 获取片元着色器的vColor成员的句柄
        GLES20.glUniform4fv(colorHandler,1,colors,0)//为三角形设置颜色
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount)//绘制三角形
        GLES20.glDisableVertexAttribArray(positionHandler)//禁用顶点数组
    }
}