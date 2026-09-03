package org.fish.Renderer;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.*;

import java.util.Optional;

import static org.fish.FishPlusPlusV2.mc;

public class Renderer {
    public static void RenderMesh(QuickMeshBuilder.Mesh mesh, Matrix4f modelView, boolean render3D) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("RenderPass");

        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

        RenderTarget mainRenderTarget = mc.gameRenderer.mainRenderTarget();
        RenderPass pass = commandEncoder.createRenderPass(() -> "FishPlusPlus RenderPass", mainRenderTarget.getColorTextureView(), Optional.empty());

        pass.setPipeline(RenderPipelines.DefaultPipeline);
        pass.setVertexBuffer(0, mesh.buffer().slice());
        pass.setIndexBuffer(mesh.indexBuffer(), mesh.indexType());

        profiler.popPush("Uniforms");

        Camera camera = mc.gameRenderer.mainCamera();

        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
                        .writeTransform(
                                render3D ? modelView : new Matrix4f(),
                                new Vector4f(1.0f),
                                render3D ? camera.position().reverse().toVector3f() : new Vector3f(0.0f),
                                new Matrix4f()
                        );

        pass.setUniform("Projection", RenderSystem.getProjectionMatrixBuffer());
        pass.setUniform("DynamicTransforms", dynamicTransforms);

        profiler.popPush("Draw");
        pass.drawIndexed(mesh.indexCount(), 1, 0, 0, 0);
        profiler.pop();
    }

}
