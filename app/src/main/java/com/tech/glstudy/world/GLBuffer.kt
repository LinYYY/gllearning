package com.tech.glstudy.world

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *  create by Myking
 *  date : 2020/12/24 18:46
 *  description :
 */
object GLBuffer {
    fun getFloatBuffer(vertexs: FloatArray): FloatBuffer {
        ///每个浮点数:坐标个数* 4字节
        val qbb = ByteBuffer.allocateDirect(vertexs.size * 4)
        //使用硬件顺序
        qbb.order(ByteOrder.nativeOrder())
        // 从字节缓冲区创建浮点缓冲区
        val floatBuffer = qbb.asFloatBuffer()
        // 将坐标添加到FloatBuffer
        floatBuffer.put(vertexs)
        //设置缓冲区以读取第一个坐标
        floatBuffer.position(0)
        return floatBuffer
    }
}