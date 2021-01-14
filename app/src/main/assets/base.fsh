#version 300 es

precision mediump float;
out vec4 outColor;
in vec4 color2frag;

void main() {
    outColor = color2frag;
}
