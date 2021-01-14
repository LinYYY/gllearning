#version 300 es
precision mediump float;

in vec2 vTexCoord;
out vec4 outColor;

uniform sampler2D sTexture;

void main() {
    //图片点阵 将图片分成若干个块，当在cell半径之内， 绘制UVMosaic点的像素值，否则，绘制白色

    float rate = 690.0/1035.0;
    float cellX = 1.0;
    float cellY = 1.0;
    float rowCount = 100.0;

//    vec2 sizeFmt=vec2(rowCount, rowCount/rate);
//    vec2 sizeMsk=vec2(cellX, cellY/rate);
//    vec2 posFmt = vec2(vTexCoord.x*sizeFmt.x, vTexCoord.y*sizeFmt.y);
//    vec2 posMsk = vec2(floor(posFmt.x/sizeMsk.x)*sizeMsk.x, floor(posFmt.y/sizeMsk.y)*sizeMsk.y)+ 0.5*sizeMsk;
//
//    float del = length(posMsk - posFmt);
//
//    vec2 UVMosaic = vec2(posMsk.x/sizeFmt.x, posMsk.y/sizeFmt.y);
//
//    vec4 result;
//    if (del< cellX/2.0)
//    result = texture(sTexture, UVMosaic);
//    else
//    result = texture(sTexture, vTexCoord);
//    outColor = result;

    float space =4.0;

    vec2 sizeFmt=vec2(rowCount, rowCount/rate);
    vec2 sizeMsk=vec2(cellX, cellY/rate);
    vec2 posFmt = vec2(vTexCoord.x*sizeFmt.x, vTexCoord.y*sizeFmt.y);
    vec2 posMsk = vec2(floor(posFmt.x/sizeMsk.x)*sizeMsk.x, floor(posFmt.y/sizeMsk.y)*sizeMsk.y)+ 0.5*sizeMsk;

    float del = length(posMsk - posFmt);
    vec2 UVMosaic = vec2(posMsk.x/sizeFmt.x, posMsk.y/sizeFmt.y);

    vec4 result;
    if (del< cellX/space)
    result=vec4(0.2,0.2,0.2,0.1);
    else
    result = texture(sTexture, vTexCoord);
    outColor = vec4(result.r,result.g,result.b,1.0);


}