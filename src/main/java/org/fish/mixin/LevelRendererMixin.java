package org.fish.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import org.fish.Renderer.QuickMeshBuilder;
import org.fish.Renderer.Renderer;
import org.fish.Renderer.WorldRenderSystem;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    public void onRenderTail(final GraphicsResourceAllocator resourceAllocator, final DeltaTracker deltaTracker, final boolean renderOutline, final CameraRenderState cameraState, final Matrix4fc modelViewMatrix, final GpuBufferSlice terrainFog, final Vector4f fogColor, final boolean shouldRenderSky, CallbackInfo ci) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("FishPlusPlus Render");

        profiler.push("Mesh build");
        WorldRenderSystem renderSystem = new WorldRenderSystem();
        renderSystem.BeginBuilding();
        renderSystem.Box(new Vec3(0, 0, 0), new Vec3(2.0f, 2.0f, 2.0f), new Color(255, 100, 100, 50), new Color(255, 100, 100, 255));
        renderSystem.EndBuilding();
        profiler.pop();

        renderSystem.Render(new Matrix4f(modelViewMatrix));
        profiler.pop();
    }
}
