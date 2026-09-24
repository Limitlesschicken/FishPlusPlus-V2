#version 450 core

layout (location = 0) in vec3 Position;
layout (location = 1) in vec4 Color;

layout(std140) uniform DynamicTransforms {
    mat4 modelView;
    mat4 textureMatrix;
    vec4 colorModulator;
    vec3 modelOffset;
};

layout(std140) uniform Projection {
    mat4 projection;
};

layout(location = 0) out vec4 color;

void main() {
    vec3 pos = Position + modelOffset;

    color = Color;
    gl_Position = projection * modelView * vec4(pos, 1.0f);
}