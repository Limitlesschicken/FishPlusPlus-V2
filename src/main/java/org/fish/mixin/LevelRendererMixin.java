package org.fish.mixin;

import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.fish.Events.EventRender3D;
import org.fish.Renderer.Renderer;
import org.fish.Renderer.WorldRenderSystem;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.fish.FishPlusPlusV2.EVENT_BUS;
import static org.fish.FishPlusPlusV2.mc;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void onRenderTail(final GraphicsResourceAllocator resourceAllocator, final boolean renderOutline, final CameraRenderState cameraState, final GpuBufferSlice terrainFog, final Vector4f fogColor, final boolean shouldRenderSky, final boolean consistentDepthRequired, CallbackInfo ci) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("FishPlusPlus Render");

        profiler.push("Mesh build");
        WorldRenderSystem renderSystem = new WorldRenderSystem();
        renderSystem.BeginBuilding();

        EVENT_BUS.post(new EventRender3D(renderSystem, mc.getDeltaTracker().getGameTimeDeltaPartialTick(false)));

        renderSystem.EndBuilding();
        profiler.pop();

        renderSystem.Render(cameraState.viewRotationMatrix);
        profiler.pop();
    }
}
