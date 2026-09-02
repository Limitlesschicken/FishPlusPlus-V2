#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;

layout(std140) uniform DynamicTransforms {
    mat4 modelView;
    vec4 colorModulator;
    vec3 modelOffset;
    mat4 textureMatrix;
};

layout(std140) uniform Projection {
    mat4 projection;
};

void main() {
    vec3 position = aPos + modelOffset;
    gl_Position = projection * modelView * vec4(position, 1.0f);
}