#version 300 es
precision mediump float;
out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D sTexture;

void main(){
    vec4 color= texture(sTexture, vTexCoord);
    outColor = vec4(color.g, color.g, color.g, 1.0);
}

