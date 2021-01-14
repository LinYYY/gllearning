package com.tech.glstudy.hello

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.InputStream


/**
 *  create by Myking
 *  date : 2020/12/11 16:15
 *  description :
 */
object GLUtils {

    //从脚本中加载shader内容的方法
    fun loadShaderAssets(ctx: Context, type: Int, name: String): Int {
        var result: String? = null
        try {
            val ins: InputStream = ctx.assets.open(name)
            var ch = 0
            val baos = ByteArrayOutputStream()
            while (ins.read().also { ch = it } != -1) {
                baos.write(ch)
            }
            val buff = baos.toByteArray()
            baos.close()
            ins.close()
            result = String(buff, Charsets.UTF_8)
            result = result.replace("\\r\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return loadShader(type, result)
    }

    /**
     * 加载作色器
     *
     * @param type       着色器类型    顶点着色 [GLES20.GL_VERTEX_SHADER]
     * 片元着色 [GLES20.GL_FRAGMENT_SHADER]
     * @param shaderCode 着色代码
     * @return 作色器
     */
    fun loadShader(type: Int, shaderCode: String?): Int {
        val shader = GLES20.glCreateShader(type) //创建着色器
        if (shader == 0) { //加载失败直接返回
            return 0
        }
        GLES20.glShaderSource(shader, shaderCode) //加载着色器源代码
        GLES20.glCompileShader(shader) //编译
        return checkCompile(type, shader)
    }

    /**
     * 检查shader代码是否编译成功
     *
     * @param type   着色器类型
     * @param shader 着色器
     * @return 着色器
     */
    private fun checkCompile(type: Int, shader: Int): Int {
        var shader = shader
        val compiled = IntArray(1) //存放编译成功shader数量的数组
        //获取Shader的编译情况
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)
        if (compiled[0] == 0) { //若编译失败则显示错误日志并
            Log.e(
                "ES20_COMPILE_ERROR",
                "Could not compile shader " + type + ":" + GLES20.glGetShaderInfoLog(shader)
            )
            GLES20.glDeleteShader(shader)
            shader = 0//删除此shader
        }
        return shader
    }
}
