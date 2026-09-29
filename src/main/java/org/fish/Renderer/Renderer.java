package org.fish.Renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.CompiledRenderPipeline;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import lombok.Getter;
import net.minecraft.client.Camera;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.Text.FontLoader;
import org.joml.*;

import java.util.Optional;
import java.util.function.Consumer;

import static org.fish.FishPlusPlusV2.mc;

public class Renderer {
    @Getter
    private static boolean initialized = false;
    public static void Init() {
        FontLoader.LoadFont("fonts/figtree.ttf", "Figtree", 24);
        initialized = true;
    }

    public static void RenderMesh(QuickMeshBuilder.Mesh mesh, Matrix4f modelView, RenderDescription description) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("RenderPass");

        if (mesh == null) {
            profiler.pop();
            return;
        }

        CompiledRenderPipeline renderPipeline = RenderSystem.getCompiledPipelineNullable(description.pipeline);
        if (renderPipeline == null) {
            FishPlusPlusV2.LOGGER.warn("Pipeline not compiled yet: {}", description.pipeline);
            profiler.pop();
            return;
        }

        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

        RenderTarget mainRenderTarget = mc.gameRenderer.mainRenderTarget();
        RenderPass pass = commandEncoder.createRenderPass(() -> "FishPlusPlus RenderPass", mainRenderTarget.getColorTextureView(), Optional.empty());

        pass.setPipeline(renderPipeline);
        pass.setVertexBuffer(0, mesh.buffer().slice());
        pass.setIndexBuffer(mesh.indexBuffer(), mesh.indexType());

        profiler.popPush("Uniforms");

        Camera camera = mc.gameRenderer.mainCamera();

        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
                        .writeTransform(
                                description.render3D ? modelView : new Matrix4f(),
                                new Vector4f(1.0f),
                                description.render3D ? camera.position().reverse().toVector3f() : new Vector3f(0.0f),
                                new Matrix4f()
                        );

        pass.setUniform("Projection", RenderSystem.getProjectionMatrixBuffer());
        pass.setUniform("DynamicTransforms", dynamicTransforms);
        if (description.uniforms != null)
            description.uniforms.accept(pass);

        profiler.popPush("Draw");
        pass.drawIndexed(mesh.indexCount(), 1, 0, 0, 0);
        profiler.pop();

        pass.close();
    }

    public static void RenderMesh(QuickMeshBuilder.Mesh mesh, RenderDescription description) {
        RenderMesh(mesh, new Matrix4f(), description);
    }

    public record RenderDescription(RenderPipeline pipeline, boolean render3D, Consumer<RenderPass> uniforms) { }
}
