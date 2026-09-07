package org.fish.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.fish.Renderer.QuickMeshBuilder;
import org.fish.Renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static org.fish.FishPlusPlusV2.mc;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    public void onRenderTail(final GraphicsResourceAllocator resourceAllocator, final DeltaTracker deltaTracker, final boolean renderOutline, final CameraRenderState cameraState, final Matrix4fc modelViewMatrix, final GpuBufferSlice terrainFog, final Vector4f fogColor, final boolean shouldRenderSky, CallbackInfo ci) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("FishPlusPlus Render");

        profiler.push("Mesh build");
        QuickMeshBuilder.Render3D meshBuilder = new QuickMeshBuilder.Render3D();
        meshBuilder.beginBuilding(false);
        meshBuilder.box(new Vec3(0.0f, 0.0f, 0.0f), 2.0, new Color(255, 100, 100, 100));
        QuickMeshBuilder.Mesh mesh = meshBuilder.endBuilding();
        profiler.pop();

        Renderer.RenderMesh(mesh, new Matrix4f(modelViewMatrix), true);
        profiler.pop();
    }
}
