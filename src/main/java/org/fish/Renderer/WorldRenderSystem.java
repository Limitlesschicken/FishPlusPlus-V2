package org.fish.Renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.awt.*;

public class WorldRenderSystem {
    public QuickMeshBuilder.Mesh mesh = null;
    public QuickMeshBuilder.Mesh meshLine = null;

    boolean building = false;

    QuickMeshBuilder.Render3D meshBuilder;
    QuickMeshBuilder.Render3D meshBuilderLine;

    public void BeginBuilding() {
        building = true;
        meshBuilder = new QuickMeshBuilder.Render3D();
        meshBuilderLine = new QuickMeshBuilder.Render3D();
        meshBuilder.BeginBuilding(false);
        meshBuilderLine.BeginBuilding(true);
    }

    public void Box(Vec3 p1, Vec3 size, Color insideColor, Color outlineColor) {
        meshBuilder.BoxSides(p1, size, insideColor);
        meshBuilderLine.BoxLines(p1, size, outlineColor);
    }

    public void Box(AABB box, Color insideColor, Color outlineColor) {
        Box(box.getMinPosition(), new Vec3(box.getXsize(), box.getYsize(), box.getZsize()), insideColor, outlineColor);
    }

    public void Box(Entity entity, Color insideColor, Color outlineColor) {
        Box(entity.getBoundingBox(), insideColor, outlineColor);
    }

    public void EndBuilding() {
        mesh = meshBuilder.EndBuilding();
        meshLine = meshBuilderLine.EndBuilding();
    }

    public void Render(Matrix4f modelView) {
        Renderer.RenderMesh(mesh, modelView, true);
        Renderer.RenderMesh(meshLine, modelView, true);
    }
}
