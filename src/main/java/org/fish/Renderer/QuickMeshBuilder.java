package org.fish.Renderer;

import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class QuickMeshBuilder {



    private BufferBuilder bufferBuilder;

    private static RenderSystem.AutoStorageIndexBuffer indices;

    public void beginBuilding() {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("BeginDrawing");

        ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(500);
        bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.QUADS, DefaultVertexFormat.POSITION_COLOR);

        //we use quads on the indices to get the right index order like "0, 1, 2, 0, 2, 3" instead of triangles which gives "1, 2, 3, 4"
        indices = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);
        profiler.pop();
    }

    public void quad(Vec3 a1, Vec3 a2, Vec3 a3, Vec3 a4, Color color) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("Building");

        bufferBuilder.addVertex((float)a1.x, (float)a1.y, (float)a1.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a2.x, (float)a2.y, (float)a2.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a3.x, (float)a3.y, (float)a3.z).setColor(color.getRGB());
        bufferBuilder.addVertex((float)a4.x, (float)a4.y, (float)a4.z).setColor(color.getRGB());

        profiler.pop();
    }

    public void quad2d(Vec2 a1, Vec2 a2, Vec2 a3, Vec2 a4, int depth, Color color) {
        quad(new Vec3(a1.x, a1.y, (float)depth), new Vec3(a2.x, a2.y, (float)depth), new Vec3(a3.x, a3.y, (float)depth), new Vec3(a4.x, a4.y, (float)depth), color);
    }

    public Mesh endBuilding() {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("EndDrawing");

        GpuBuffer renderBuffer = RenderSystem.getDevice().createBuffer(() -> "FishplusPlus buffer", 40, 500);

        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        try (MeshData meshData = bufferBuilder.buildOrThrow()) {
            commandEncoder.writeToBuffer(renderBuffer.slice(), meshData.vertexBuffer());

            //set buffer size to index count
            GpuBuffer indexBuffer = indices.getBuffer(meshData.drawState().indexCount());

            Mesh mesh = new Mesh(renderBuffer, indexBuffer, indices.type(), meshData.drawState().indexCount());
            profiler.pop();
            return mesh;
        }
    }

    public record Mesh(GpuBuffer buffer, GpuBuffer indexBuffer, IndexType indexType, int indexCount) {
    }
}
