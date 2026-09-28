#version 450 core

layout(location = 0) out vec4 FragColor;

layout(std140) uniform DynamicTransforms {
    mat4 modelView;
    mat4 textureMatrix;
    vec4 colorModulator;
    vec3 modelOffset;
};

layout(location = 0) in vec4 color;
layout(location = 1) in vec2 uv;

uniform sampler2D Sampler0;

void main() {
    vec4 sampled = vec4(1.0, 1.0, 1.0, texture(Sampler0, uv).a);
    FragColor = color * sampled;
}