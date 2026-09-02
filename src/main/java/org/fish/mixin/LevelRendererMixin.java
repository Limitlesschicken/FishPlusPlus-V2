package org.fish.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.fish.Renderer.Renderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void onRenderTail(final GraphicsResourceAllocator resourceAllocator, final DeltaTracker deltaTracker, final boolean renderOutline, final CameraRenderState cameraState, final Matrix4fc modelViewMatrix, final GpuBufferSlice terrainFog, final Vector4f fogColor, final boolean shouldRenderSky, CallbackInfo ci) {
        Renderer.Render(new Matrix4f(modelViewMatrix));
    }
}
