package org.fish.Renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.CompiledRenderPipeline;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.Camera;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.Text.Character;
import org.fish.Renderer.Text.Font;
import org.fish.Renderer.Text.FontLoader;
import org.joml.*;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

import static org.fish.FishPlusPlusV2.mc;

public class Renderer {
    public static void RenderMesh(QuickMeshBuilder.Mesh mesh, Matrix4f modelView, RenderDescription description) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("RenderPass");

        if (mesh == null) {
            profiler.pop();
            return;
        }

        CompiledRenderPipeline renderPipeline = RenderSystem.getCompiledPipelineNullable(description.pipeline);
        if (renderPipeline == null) {
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

    static boolean yes = true;
    static Font font;

    public static QuickMeshBuilder.Mesh CreateTextMesh() {
        String text = "Hello :)";

        QuickMeshBuilder.Render2D meshBuilder = new QuickMeshBuilder.Render2D();
        meshBuilder.BeginBuilding(false, true);

        float scale = 1.0f;
        float x = 100;
        float y = 100;

        for (char c : text.toCharArray()) {
            Character ch = font.characters().get(c);

            float w = ch.size().x * scale;
            float h = ch.size().y * scale;

            float baseline = y + ch.Ascender() * scale;

            float xPos = x + ch.bearing().x * scale;
            float yPos = (baseline - ch.bearing().y * scale);

            x += ch.Advance() * scale;

            float u0 = ch.tx();
            float u1 = ch.tx() + ch.size().x / (float) font.atlasWidth();

            float v0 = 0.0f;
            float v1 = ch.size().y / (float) font.atlasHeight();

            meshBuilder.quadUV(
                    new Vec3(xPos, yPos, -1.0f),
                    new Vec3(xPos + w, yPos, -1.0f),
                    new Vec3(xPos, yPos + h, -1.0f),
                    new Vec3(xPos + w, yPos + h, -1.0f),
                    new Vec2(u1, v0),
                    new Vec2(u0, v0),
                    new Vec2(u0, v1),
                    new Vec2(u1, v1),
                    Color.WHITE
            );
        }

        return meshBuilder.EndBuilding();
    }
    public static void TextRenderer() {

        if (yes)
            font = FontLoader.LoadFont();

        yes = false;

        QuickMeshBuilder.Mesh mesh = CreateTextMesh();

        RenderMesh(mesh, new RenderDescription(RenderPipelines.TextPipeline, false, pass -> {
            pass.setUniform("Sampler0", font.texture().getTextureView(), font.texture().getSampler());
        }));
    }

}
