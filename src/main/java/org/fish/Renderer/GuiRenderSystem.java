package org.fish.Renderer;

import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        Renderer.RenderMesh(node.meshLine, new Matrix4f(), false);
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
        public QuickMeshBuilder.Mesh meshLine = null;

        boolean building = false;
        QuickMeshBuilder.Render2D meshBuilder;
        QuickMeshBuilder.Render2D meshBuilderLine;

        protected void Begin() {
            building = true;
            meshBuilder = new QuickMeshBuilder.Render2D();
            meshBuilderLine = new QuickMeshBuilder.Render2D();
            meshBuilder.beginBuilding(false);
            meshBuilderLine.beginBuilding(true);
        }

        protected void End() {
            if (!building)
                throw new RuntimeException("Not building gui mesh");
            building = false;
            mesh = meshBuilder.endBuilding();
            meshLine = meshBuilderLine.endBuilding();
        }

        public void Quad(Vec2 a1, int width, int height, Color color) {
            meshBuilder.Rect(a1, width, height, color);
        }
        public void Line(Vec2 a1, Vec2 a2, Color color) {
            meshBuilderLine.Line(a1, a2, color);
        }
    }

}
