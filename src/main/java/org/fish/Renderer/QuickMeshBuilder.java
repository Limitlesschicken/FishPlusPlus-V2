package org.fish.Renderer;

import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.pipeline.IndexType;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.fish.Renderer.Text.Character;
import org.fish.Renderer.Text.Font;

import java.awt.*;

public abstract class QuickMeshBuilder {
    private BufferBuilder bufferBuilder;

    private RenderSystem.AutoStorageIndexBuffer indices;

    boolean line = false;

    public void BeginBuilding(boolean line, boolean textured) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("BeginBuilding");

        ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(5000);
        bufferBuilder = new BufferBuilder(
                byteBufferBuilder,
                line ? PrimitiveTopology.DEBUG_LINES : PrimitiveTopology.QUADS,
                textured ? DefaultVertexFormat.POSITION_TEX_COLOR : DefaultVertexFormat.POSITION_COLOR);

        //we use quads on the indices to get the right index order like "0, 1, 2, 0, 2, 3" instead of triangles which gives "1, 2, 3, 4"
        indices = RenderSystem.getSequentialBuffer(line ? PrimitiveTopology.DEBUG_LINES : PrimitiveTopology.QUADS);

        this.line = line;
        profiler.pop();
    }

    protected void quad(Vec3 a1, Vec3 a2, Vec3 a3, Vec3 a4, Color color) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("Building");

        bufferBuilder.addVertex((float)a2.x, (float)a2.y, (float)a2.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a1.x, (float)a1.y, (float)a1.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a3.x, (float)a3.y, (float)a3.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a4.x, (float)a4.y, (float)a4.z).setColor(color.getRGB());

        profiler.pop();
    }

    protected void quadUV(Vec3 a1, Vec3 a2, Vec3 a3, Vec3 a4, Vec2 uv1, Vec2 uv2, Vec2 uv3, Vec2 uv4, Color color) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("Building");

        bufferBuilder.addVertex((float)a2.x, (float)a2.y, (float)a2.z).setUv(uv1.x, uv1.y).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a1.x, (float)a1.y, (float)a1.z).setUv(uv2.x, uv2.y).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a3.x, (float)a3.y, (float)a3.z).setUv(uv3.x, uv3.y).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a4.x, (float)a4.y, (float)a4.z).setUv(uv4.x, uv4.y).setColor(color.getRGB());

        profiler.pop();
    }

    protected void line(Vec3 a1, Vec3 a2, Color color) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("Building");

        bufferBuilder.addVertex((float)a2.x, (float)a2.y, (float)a2.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a1.x, (float)a1.y, (float)a1.z).setColor(color.getRGB());

        profiler.pop();
    }

    public Mesh EndBuilding() {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("EndBuilding");

        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        MeshData meshData = bufferBuilder.build();
        if (meshData == null) {
            profiler.pop();
            return null;
        }

        GpuBuffer renderBuffer = RenderSystem.getDevice().createBuffer(() -> "FishplusPlus buffer", 40, meshData.vertexBuffer().remaining());

        commandEncoder.writeToBuffer(renderBuffer.slice(), meshData.vertexBuffer());

        //set buffer size to index count
        GpuBuffer indexBuffer = indices.getBuffer(meshData.drawState().indexCount());

        Mesh mesh = new Mesh(renderBuffer, indexBuffer, indices.type(), meshData.drawState().indexCount(), this.line);
        profiler.pop();
        return mesh;

    }

    public record Mesh(GpuBuffer buffer, GpuBuffer indexBuffer, IndexType indexType, int indexCount, boolean line) {
    }

    public static class Render3D extends QuickMeshBuilder {
        public void BoxSides(Vec3 p1, Vec3 size, Color color) {
            quad(p1.add(new Vec3(size.x, size.y, size.z)), p1.add(new Vec3(size.x, 0, size.z)), p1.add(new Vec3(size.x, size.y, 0)), p1.add(new Vec3(size.x, 0, 0)), color);
            quad(p1.add(new Vec3(size.x, 0, 0)), p1.add(new Vec3(size.x, size.y, 0)), p1.add(new Vec3(0, 0, 0)), p1.add(new Vec3(0, size.y, 0)), color);
            quad(p1.add(new Vec3(0, size.y, 0)), p1.add(new Vec3(0, size.y, size.z)), p1.add(new Vec3(size.x, size.y, 0)), p1.add(new Vec3(size.x, size.y, size.z)), color);
            quad(p1.add(new Vec3(0, 0, 0)), p1.add(new Vec3(0, 0, size.z)), p1.add(new Vec3(size.x, 0, 0)), p1.add(new Vec3(size.x, 0, size.z)), color);
            quad(p1.add(new Vec3(size.x, size.y, size.z)), p1.add(new Vec3(size.x, 0, size.z)), p1.add(new Vec3(0, size.y, size.z)), p1.add(new Vec3(0, 0, size.z)), color);
            quad(p1.add(new Vec3(0, 0, 0)), p1.add(new Vec3(0, 0, size.z)), p1.add(new Vec3(0, size.y, 0)), p1.add(new Vec3(0, size.y, size.z)), color);
        }

        public void BoxLines(Vec3 p1, Vec3 size, Color color) {
            Line(p1, p1.add(size.x, 0, 0), color);
            Line(p1, p1.add(0, size.y, 0), color);
            Line(p1, p1.add(0, 0, size.z), color);

            Line(p1.add(size.x, size.y, size.z), p1.add(size.x, size.y, 0), color);
            Line(p1.add(size.x, size.y, size.z), p1.add(0, size.y, size.z), color);
            Line(p1.add(size.x, size.y, size.z), p1.add(size.x, 0, size.z), color);

            Line(p1.add(size.x, 0, 0), p1.add(size.x, 0, size.z), color);
            Line(p1.add(size.x, 0, 0), p1.add(size.x, size.y, 0), color);
            Line(p1.add(size.x, size.y, 0), p1.add(0, size.y, 0), color);

            Line(p1.add(0, 0, size.z), p1.add(size.x, 0, size.z), color);
            Line(p1.add(0, 0, size.z), p1.add(0, size.y, size.z), color);
            Line(p1.add(0, size.y, size.z), p1.add(0, size.y, 0), color);

        }

        public void Line(Vec3 p1, Vec3 p2, Color color) {
            line(p1, p2, color);
        }
    }

    public static class Render2D extends QuickMeshBuilder {
        public void Rect(Vec2 p1, int width, int height, Color color) {
            quad(new Vec3(p1.x, p1.y, -1.0f), new Vec3(p1.x + width, p1.y, -1.0f), new Vec3(p1.x, p1.y + height, -1.0f), new Vec3(p1.x + width, p1.y + height, -1.0f), color);
        }

        public void Text(String text, Vec2 pos, float scale, Color color, Font font) {
            if (font == null)
                return;
            ProfilerFiller profiler = Profiler.get();
            profiler.push("Text building");
            float x = pos.x;
            for (char c : text.toCharArray()) {

                Character ch = font.characters().get(c);

                float w = ch.size().x * scale;
                float h = ch.size().y * scale;

                float baseline = pos.y + ch.Ascender() * scale;

                float xPos = x + ch.bearing().x * scale;
                float yPos = (baseline - ch.bearing().y * scale);

                x += ch.Advance() * scale;

                float u0 = ch.tx();
                float u1 = ch.tx() + ch.size().x / (float) font.atlasWidth();

                float v0 = 0.0f;
                float v1 = ch.size().y / (float) font.atlasHeight();

                quadUV(
                        new Vec3(xPos, yPos, -1.0f),
                        new Vec3(xPos + w, yPos, -1.0f),
                        new Vec3(xPos, yPos + h, -1.0f),
                        new Vec3(xPos + w, yPos + h, -1.0f),
                        new Vec2(u1, v0),
                        new Vec2(u0, v0),
                        new Vec2(u0, v1),
                        new Vec2(u1, v1),
                        color
                );
            }
            profiler.pop();
        }

        public void Line(Vec2 p1, Vec2 p2, Color color) {
            line(new Vec3(p1.x, p1.y, -1.0f), new Vec3(p2.x, p2.y, -1.0f), color);
        }
    }
}
