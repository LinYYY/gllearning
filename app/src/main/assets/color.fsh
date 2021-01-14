#version 300 es
precision mediump float;
out vec4 outColor;

in vec2 vTexCoord;

uniform sampler2D sTexture;

uniform float uThreshold;//阈值 入参

void main() {
    vec2 pos = vTexCoord.xy;
    vec4 result;
    //x反向
    //    pos.x = 1.0 - pos.x;
    //y反向
    //    pos.y = 1.0 - pos.y;

    //分屏  根据像素位置判断，去修改读取纹理的位置坐标，从而影响渲染
    //    if (pos.y < 0.5) {
    //        pos.y = pos.y;
    //    } else {
    //        pos.y = pos.y - 0.5;
    //    }

    //左右分镜
    //    if (pos.x > 0.5) {
    //        pos.x = 1.0 - pos.x;
    //    }

    //三分
    //    if (pos.y<= 1.0/3.0) {
    //        pos.y = pos.y * 1.0;
    //    } else if (pos.y<= 2.0/3.0){
    //        pos.y = (pos.y - 1.0/3.0) * 1.0;
    //    } else {
    //        pos.y = (pos.y - 2.0/3.0) * 1.0;
    //    }

    //四份
    //    if (pos.x < 0.5) {
    //        pos.x = pos.x;
    //    } else {
    //        pos.x = pos.x - 0.5;
    //    }
    //    if (pos.y < 0.5) {
    //        pos.y = pos.y;
    //    } else {
    //        pos.y = pos.y - 0.5;
    //    }

    //四个小图
    //    if (pos.x <= 0.5){
    //        pos.x = pos.x * 2.0;
    //    } else {
    //        pos.x = (pos.x - 0.5) * 2.0;
    //    }
    //
    //    if (pos.y<= 0.5) {
    //        pos.y = pos.y * 2.0;
    //    } else {
    //        pos.y = (pos.y - 0.5) * 2.0;
    //    }

    //    vec4 color = texture(sTexture, pos);
    //    float r = color.r;
    //    float g = color.g;
    //    float b = color.b;

    //黑白
    //    g = r * 0.3 + g * 0.59 + b * 0.11;
    //    g = g <= uThreshold ? 0.0 : 1.0;

    //负片
    //    r = 1.0 - color.r;
    //    g = 1.0 - color.g;
    //    b = 1.0 - color.b;

    //怀旧  是将图片变成偏黄的暖调，
    //    r = 0.393 * r + 0.769 * g + 0.189 * b;
    //    g = 0.349 * r + 0.686 * g + 0.168 * b;
    //    b = 0.272 * r + 0.534 * g + 0.131 * b;


    //冷调 中只是将r和b的色值进行对调，就能达到相反的效果
    //    b = 0.393 * r + 0.769 * g + 0.189 * b;
    //    g = 0.349 * r + 0.686 * g + 0.168 * b;
    //    r = 0.272 * r + 0.534 * g + 0.131 * b;


    //流年 arg越大
    //    float arg = 3.3;
    //    b = sqrt(b) * arg;
    //    if (b>1.0) b = 1.0;

//    result = vec4(r, g, b, 1.0);

    //分区特效
    if (pos.x < 0.5 && pos.y < 0.5) {
        //左上
        pos.x = pos.x * 2.0;
        pos.y = pos.y * 2.0;
        //原色
        vec4 color = texture(sTexture, pos);
        result = vec4(color.r, color.g, color.b, 1.0);
    } else if (pos.x >=0.5 && pos.y < 0.5) {
        //右上
        pos.x = (pos.x - 0.5) * 2.0;
//        pos.y = (pos.y - 0.5) * 2.0;
        pos.y = pos.y * 2.0;
        vec4 color = texture(sTexture, pos);
        float r = color.r;
        float g = color.g;
        float b = color.b;
        //负片
        r = 1.0 - color.r;
        g = 1.0 - color.g;
        b = 1.0 - color.b;
        result = vec4(r, g, b, 1.0);
    } else if (pos.x < 0.5 && pos.y >= 0.5) {
        //左下
        pos.x = pos.x * 2.0;
        pos.y = (pos.y - 0.5) * 2.0;
        vec4 color= texture(sTexture, pos);
        float r = color.r;
        float g = color.g;
        float b = color.b;
        //怀旧
        r = 0.393 * r + 0.769 * g + 0.189* b;
        g = 0.349 * r + 0.686 * g + 0.168 * b;
        b = 0.272 * r + 0.534 * g + 0.131 * b;
        result = vec4(r, g, b, 1.0);
    } else if (pos.x >= 0.5 && pos.y >= 0.5) {
        //右下
        pos.x = (pos.x - 0.5) * 2.0;
        pos.y = (pos.y - 0.5) * 2.0;

        vec4 color= texture(sTexture, pos);
        float r = color.r;
        float g = color.g;
        float b = color.b;
        //冷调
        b = 0.393 * r + 0.769 * g + 0.189* b;
        g = 0.349 * r + 0.686 * g + 0.168 * b;
        r = 0.272 * r + 0.534 * g + 0.131 * b;
        result = vec4(r, g, b, 1.0);
    }

    outColor = result;
}