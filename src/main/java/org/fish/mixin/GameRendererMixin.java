package org.fish.mixin;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.fish.Events.EventRender2D;
import org.fish.FishPlusPlusV2;
import org.fish.Gui.Gui;
import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.Renderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.fish.FishPlusPlusV2.EVENT_BUS;
import static org.fish.FishPlusPlusV2.mc;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Unique
    private static final ProjectionMatrixBuffer matrixBuffer = new ProjectionMatrixBuffer("matrixBuffer");

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        if (!Renderer.isInitialized() || !mc.isGameLoadFinished())
            return;

        ProfilerFiller profiler = Profiler.get();
        profiler.push("FishPlusPlus 2D Render");

        Projection proj = new Projection();
        proj.setupOrtho(0.1f, 1000.0f, mc.getWindow().getWidth(), mc.getWindow().getHeight(), true);

        RenderSystem.setProjectionMatrix(matrixBuffer.getBuffer(proj.getMatrix(new Matrix4f())), ProjectionType.ORTHOGRAPHIC);

        GuiRenderSystem guiRenderer = new GuiRenderSystem();

        GuiRenderSystem.Node backGround = guiRenderer.NextStratum();

        profiler.push("Build");

        //First node is reserved for module 2d rendering
        EVENT_BUS.post(new EventRender2D(backGround, mc.getDeltaTracker().getGameTimeDeltaPartialTick(false)));

        FishPlusPlusV2.GUI.Update();
        FishPlusPlusV2.GUI.Render(guiRenderer);

        profiler.popPush("Render");

        guiRenderer.Render();

        profiler.pop();
        profiler.pop();
    }
}
