#version 300 es
precision mediump float;
in vec2 vTexCoord;
out vec4 outColor;

uniform sampler2D sTexture;

void main() {

    float rate = 690.0/1035.0;
    float cellX = 1.0;//马赛克格子大小
    float cellY = 1.0;
    float rowCount = 100.0;//马赛克格子个数

    float centerX = 0.5;
    float centerY = 0.46/rate;
    float radius = 0.1;

    vec2 pos;
    pos.x = vTexCoord.x;
    pos.y = vTexCoord.y/rate;

    float distance = sqrt((pos.x-centerX)*(pos.x-centerX)+(pos.y-centerY)*(pos.y-centerY));

    if (distance<radius){ //表示在圆的区域内
        //局部马赛克
        vec2 pos = vTexCoord.xy;
        pos.x = pos.x * rowCount;
        pos.y = pos.y * rowCount / rate;

        pos = vec2(floor(pos.x/cellX)*cellX/rowCount, floor(pos.y/cellY)*cellY/(rowCount/rate))+ 0.5/rowCount*vec2(cellX, cellY);
        outColor = texture(sTexture, pos);
    } else {
        outColor = texture(sTexture, vTexCoord);
    }


}