package org.fish.mixin;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.world.phys.Vec2;
import org.fish.Events.EventRender2D;
import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.QuickMeshBuilder;
import org.fish.Renderer.Renderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
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

    @Inject(method = "render", at = @At("TAIL"))
    public void render(final DeltaTracker deltaTracker, final boolean advanceGameTime, CallbackInfo ci) {

        Projection proj = new Projection();
        proj.setupOrtho(0.1f, 1000.0f, mc.getWindow().getWidth(), mc.getWindow().getHeight(), true);

        RenderSystem.setProjectionMatrix(matrixBuffer.getBuffer(proj.getMatrix(new Matrix4f())), ProjectionType.ORTHOGRAPHIC);

        QuickMeshBuilder.Render2D render2D = new QuickMeshBuilder.Render2D();

        render2D.beginBuilding();
        EVENT_BUS.post(new EventRender2D(render2D));
        Renderer.RenderMesh(render2D.endBuilding(), new Matrix4f(), false);

        GuiRenderSystem guiRenderer = new GuiRenderSystem();

        GuiRenderSystem.Node backGround = guiRenderer.NextStratum();
        backGround.Quad(new Vec2(0, 0), 100, 100, new Color(255, 0, 0, 255));

        GuiRenderSystem.Node foreGround = guiRenderer.NextStratum();
        foreGround.Quad(new Vec2(50, 0), 100, 100, new Color(0, 255, 0, 255));

        guiRenderer.Render();
    }
}
