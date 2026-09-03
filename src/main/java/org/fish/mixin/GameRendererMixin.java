package org.fish.mixin;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.world.phys.Vec2;
import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.QuickMeshBuilder;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static org.fish.FishPlusPlusV2.mc;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Unique
    private static final ProjectionMatrixBuffer matrixBuffer = new ProjectionMatrixBuffer("matrixBuffer");

    @Inject(method = "render", at = @At("TAIL"))
    public void render(final DeltaTracker deltaTracker, final boolean advanceGameTime, CallbackInfo ci) {
        GuiRenderSystem guiRenderer = new GuiRenderSystem();
        guiRenderer.NextStratum();

        QuickMeshBuilder meshBuilder = new QuickMeshBuilder();
        meshBuilder.beginBuilding();
        meshBuilder.quad2d(new Vec2(0, 0), new Vec2(100, 0), new Vec2(100, 100), new Vec2(0, 100), -1, new Color(255, 0, 0, 50));
        QuickMeshBuilder.Mesh mesh = meshBuilder.endBuilding();

        GuiRenderSystem.GuiDrawCommand command = new GuiRenderSystem.GuiDrawCommand(mesh);

        guiRenderer.AddDrawCommand(command);

        Projection proj = new Projection();
        proj.setupOrtho(0.1f, 1000.0f, mc.getWindow().getWidth(), mc.getWindow().getHeight(), true);

        RenderSystem.setProjectionMatrix(matrixBuffer.getBuffer(proj.getMatrix(new Matrix4f())), ProjectionType.ORTHOGRAPHIC);
        guiRenderer.Render(new Matrix4f());
    }
}
