#version 300 es
//使用samplerExternalOES纹理需要#extension GL_OES_EGL_image_external_essl3
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main() {
    vec3 color = texture(sTexture, texCoo2Frag).rgb;//获取rgb值
    float threshold = 0.5;//通道阈值
    float mean = (color.r + color.g + color.b) / 3.0;
    color.g = color.b = mean >= threshold ? 1.0 : 0.0;//平均值大于阈值 设为白色：1.0 否则为0
    outColor = vec4(1, color.gb, 1);//只保留红色
    //    outColor = vec4(color.rg, 1, 1);//只保留蓝色
}