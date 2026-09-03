package org.fish.Renderer;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.world.phys.Vec2;
import org.fish.FishPlusPlusV2;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.fish.FishPlusPlusV2.mc;

public class GuiRenderSystem {
    private final List<GuiRenderSystem.Node> strata = new ArrayList<>();
    private GuiRenderSystem.Node current;

    public void Render() {
        if (this.current != null) {
            this.current.End();
        }


        for (GuiRenderSystem.Node node : strata) {
            RenderNode(node);
        }
    }

    private void RenderNode(GuiRenderSystem.Node node) {
        Renderer.RenderMesh(node.mesh, new Matrix4f(), false);
    }

    public Node NextStratum() {
        if (this.current != null) {
            this.current.End();
        }

        this.current = new GuiRenderSystem.Node();
        this.current.Begin();
        this.strata.add(this.current);
        return this.current;
    }

    public class Node {
        public QuickMeshBuilder.Mesh mesh = null;

        boolean building = false;
        QuickMeshBuilder.Render2D meshBuilder;

        protected void Begin() {
            building = true;
            meshBuilder = new QuickMeshBuilder.Render2D();
            meshBuilder.beginBuilding();
        }

        protected void End() {
            if (!building)
                throw new RuntimeException("Not building gui mesh");
            mesh = meshBuilder.endBuilding();
        }

        public void Quad(Vec2 a1, int height, int width, Color color) {
            meshBuilder.Rect(a1, width, height, color);
        }
    }

}
