package org.fish.Renderer;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import org.joml.*;

import java.util.Optional;
import java.util.function.Supplier;

import static org.fish.FishPlusPlusV2.mc;

public class Renderer {

    private static GpuBuffer renderBuffer = RenderSystem.getDevice().createBuffer(() -> "FishplusPlus buffer", 40, 500);
    private static RenderSystem.AutoStorageIndexBuffer indices = RenderSystem.getSequentialBuffer(PrimitiveTopology.TRIANGLES);

    public static void Render(Matrix4f modelView) {
        //building mesh for initial rendering test
        ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(500);
        BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        bufferBuilder.addVertex(0, 0, 0).setColor(255, 255, 255, 255);
        bufferBuilder.addVertex(10, 0, 0).setColor(255, 255, 255, 255);
        bufferBuilder.addVertex(10, 10, 0).setColor(255, 255, 255, 255);

        try (MeshData meshData = bufferBuilder.buildOrThrow()) {
            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(renderBuffer.slice(), meshData.vertexBuffer());
        }


        RenderTarget mainRenderTarget = mc.gameRenderer.mainRenderTarget();
        Optional<Vector4fc> optionalColor = Optional.empty();
        Supplier<String> supplier = () -> "FishPlusPlus RenderPass";
        RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(supplier, mainRenderTarget.getColorTextureView(), optionalColor);

        GpuBuffer indexBuffer = indices.getBuffer(3);

        pass.setPipeline(RenderPipelines.DefaultPipeline);
        pass.setVertexBuffer(0, renderBuffer.slice());
        pass.setIndexBuffer(indexBuffer, indices.type());

        Camera camera = mc.gameRenderer.mainCamera();

        Matrix4f view = new Matrix4f(modelView);

        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
                        .writeTransform(
                                view,
                                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                                new Vector3f((float)(-camera.position().x), (float)(-camera.position().y), (float)(-camera.position().z)),
                                new Matrix4f()
                        );

        pass.setUniform("Projection", RenderSystem.getProjectionMatrixBuffer());
        pass.setUniform("DynamicTransforms", dynamicTransforms);


        pass.drawIndexed(3, 1, 0, 0, 0);
    }

}
