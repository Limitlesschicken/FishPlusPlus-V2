package org.fish.mixin;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.world.phys.Vec2;
import org.fish.Events.EventRender2D;
import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.QuickMeshBuilder;
import org.fish.Renderer.Renderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static org.fish.FishPlusPlusV2.EVENT_BUS;
import static org.fish.FishPlusPlusV2.mc;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Unique
    private static final ProjectionMatrixBuffer matrixBuffer = new ProjectionMatrixBuffer("matrixBuffer");

    @Shadow
    private final GameRenderState gameRenderState = new GameRenderState();

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        if (!gameRenderState.shouldRenderLevel)
            return;
        Projection proj = new Projection();
        proj.setupOrtho(0.1f, 1000.0f, mc.getWindow().getWidth(), mc.getWindow().getHeight(), true);

        RenderSystem.setProjectionMatrix(matrixBuffer.getBuffer(proj.getMatrix(new Matrix4f())), ProjectionType.ORTHOGRAPHIC);

        GuiRenderSystem guiRenderer = new GuiRenderSystem();

        GuiRenderSystem.Node backGround = guiRenderer.NextStratum();
        EVENT_BUS.post(new EventRender2D(backGround));

        guiRenderer.Render();
    }
}
