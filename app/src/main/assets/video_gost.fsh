//灵魂出窍效果
#version 300 es
//使用samplerExternalOES纹理需要#extension GL_OES_EGL_image_external_essl3
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

layout (location = 5) uniform float uProgress;

void main() {
    //周期
    float duration = 0.7;
    //生成第二个图层的最大透明值
    float maxAlpha = 0.3;
    //第二个图层的放大的最大比率
    float maxScale = 1.8;
    //进度
    float progress = mod(uProgress, duration) / duration;//0~1
    //当前透明度
    float alpha = maxAlpha * (1.0 - progress);
    //当前放大比例
    float scale = 1.0 + (maxScale - 1.0) * progress;

    //根据放大的比率获取位置
    float weakX = 0.5 + (texCoo2Frag.x - 0.5) / scale;
    float weakY = 0.5 + (texCoo2Frag.y - 0.5) / scale;
    //新图层纹理坐标
    vec2 weakTextureCoord = vec2(weakX, weakY);

    //新图层纹理坐标对应的像素值
    vec4 weakMask = texture(sTexture, weakTextureCoord);

    vec4 mask = texture(sTexture, texCoo2Frag);

    //纹理像素值的混合公式,获得混合后的颜色值
    outColor = mask * (1.0 - alpha) + weakMask * alpha;
}