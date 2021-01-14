#version 300 es
//使用samplerExternalOES纹理需要#extension GL_OES_EGL_image_external_essl3
#extension GL_OES_EGL_image_external_essl3 : require
precision highp float;

in vec2 texCoo2Frag;
out vec4 outColor;

layout (location = 4) uniform samplerExternalOES sTexture;

void main() {
    outColor = texture(sTexture, texCoo2Frag);
}
