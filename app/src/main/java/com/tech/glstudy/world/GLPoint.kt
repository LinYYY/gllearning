package com.tech.glstudy.world

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES30
import com.tech.glstudy.world.GLBuffer.getFloatBuffer
import com.tech.glstudy.world.GLLoader.loadShader
import java.nio.FloatBuffer


class GLPoint(context: Context) {
    //顶点着色代码
    val vsh = """#version 300 es
layout (location = 0) in vec3 aPosition; 
layout (location = 1) in vec4 aColor;

uniform mat4 uMVPMatrix;

out vec4 color2frag;

void main(){
    gl_Position = uMVPMatrix * vec4(aPosition.x,aPosition.y, aPosition.z, 1.0);
    color2frag = aColor;
gl_PointSize=10.0;}"""

    //片段着色代码
    val fsh = """#version 300 es
precision mediump float;
out vec4 outColor;
in vec4 color2frag;

void main(){
    outColor = color2frag;
}"""

    //顶点数组
    private val vertexes = floatArrayOf( //以逆时针顺序
        0.0f, 0.0f, 0.0f,//原点
        0.5f, 0.0f, 0.0f,
        0.5f, 0.5f, 0.0f,
        0.0f, 0.5f, 0.0f
    )

    // 颜色数组
    private val colors = floatArrayOf(
        1.0f, 1.0f, 1.0f, 1.0f,//白色
        1.0f, 0.0f, 0.0f, 1.0f,//红色
        0.0f, 1.0f, 0.0f, 1.0f,//绿色
        0.0f, 0.0f, 1.0f, 1.0f//蓝色
    )
    private val program: Int
    private var vertBuffer: FloatBuffer
    private var colorBuffer: FloatBuffer
    private val aPosition = 0 //位置的句柄
    private val aColor = 1 //颜色的句柄
    private var uMVPMatrix = 2 //矩阵变换的句柄
    private fun initProgram(): Int {
        ////顶点shader代码加载
        val vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vsh)
        //片段shader代码加载
        val fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fsh)
        val program: Int = GLES30.glCreateProgram() //创建空的OpenGL ES 程序
        GLES30.glAttachShader(program, vertexShader) //加入顶点着色器
        GLES30.glAttachShader(program, fragmentShader) //加入片元着色器
        GLES30.glLinkProgram(program) //创建可执行的OpenGL ES项目
        return program
    }

    fun draw(mvpMatrix: FloatArray) {
        //清除颜色缓存和深度缓存
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        // 将程序添加到OpenGL ES环境中
        GLES30.glUseProgram(program)
        //启用顶点句柄
        GLES30.glEnableVertexAttribArray(aPosition)
        //启用颜色句柄
        GLES30.glEnableVertexAttribArray(aColor)
        //应用矩阵
        GLES30.glUniformMatrix4fv(uMVPMatrix, 1, false, mvpMatrix, 0)
        //准备坐标数据
        GLES30.glVertexAttribPointer(
            aPosition, VERTEX_DIMENSION,
            GLES30.GL_FLOAT, false,
            VERTEX_DIMENSION * 4, vertBuffer
        )

        //准备颜色数据
        GLES30.glVertexAttribPointer(
            aColor, COLOR_DIMENSION,
            GLES30.GL_FLOAT, false,
            COLOR_DIMENSION * 4, colorBuffer
        )
        GLES30.glLineWidth(10f)
        //绘制点
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vertexes.size / VERTEX_DIMENSION)
        GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, vertexes.size / VERTEX_DIMENSION)

        //禁用顶点数组
        GLES30.glDisableVertexAttribArray(aPosition)
        GLES30.glDisableVertexAttribArray(aColor)
    }

    companion object {
        private const val VERTEX_DIMENSION = 3
        private const val COLOR_DIMENSION = 4
    }

    init {
        program = GLLoader.initProgramFromAssets(context, "base.fsh", "base.vsh")
        vertBuffer = getFloatBuffer(vertexes)
        colorBuffer = getFloatBuffer(colors)
        uMVPMatrix = GLES30.glGetUniformLocation(program, "uMVPMatrix")
    }
}