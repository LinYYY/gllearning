package com.tech.glstudy.world

import android.content.Context
import android.opengl.GLES30
import java.lang.Exception
import java.lang.StringBuilder

/**
 *  create by Myking
 *  date : 2020/12/24 18:26
 *  description :
 */
object GLLoader {
    /**
     * 加载着色器
     * @param type 顶点着色[GLES30.GL_VERTEX_SHADER] 片元着色[GLES30.GL_FRAGMENT_SHADER]
     * @param shaderCode 着色代码
     * @return 着色器
     */
    fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES30.glCreateShader(type)//创建着色器
        GLES30.glShaderSource(shader, shaderCode)//添加着色器源代码
        GLES30.glCompileShader(shader)//编译
        return shader
    }

    fun initProgramFromAssets(context: Context, fshName: String, vshName: String): Int {
        ////顶点shader代码加载
        val vertexShader = loadShaderFromAssest(context, GLES30.GL_VERTEX_SHADER, vshName)
        //片段shader代码加载
        val fragmentShader = loadShaderFromAssest(context, GLES30.GL_FRAGMENT_SHADER, fshName)
        val program: Int = GLES30.glCreateProgram() //创建空的OpenGL ES 程序
        GLES30.glAttachShader(program, vertexShader) //加入顶点着色器
        GLES30.glAttachShader(program, fragmentShader) //加入片元着色器
        GLES30.glLinkProgram(program) //创建可执行的OpenGL ES项目
        return program
    }

    /**
     * 从assets文件夹中加载shader
     */
    private fun loadShaderFromAssest(context: Context, type: Int, name: String): Int {
        val bytes = ByteArray(1024)
        var len = 0
        val sb = StringBuilder()
        try {
            val ins = context.assets.open(name)
            while (ins.read(bytes).also { len = it } != -1) {
                sb.append(String(bytes, 0, len))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return loadShader(type, sb.toString())
    }
}