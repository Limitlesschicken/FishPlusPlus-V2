#version 450 core

layout (location = 0) in vec3 Position;
layout (location = 1) in vec2 UV0;
layout (location = 2) in vec4 Color;

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
layout(location = 1) out vec2 uv;

void main() {
    color = Color;
    uv = UV0;
    gl_Position = projection * modelView * vec4(Position, 1.0f);
}