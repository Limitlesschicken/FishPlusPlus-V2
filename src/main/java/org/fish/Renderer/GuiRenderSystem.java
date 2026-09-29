package org.fish.Renderer;

import net.minecraft.world.phys.Vec2;
import org.fish.Renderer.Text.FontLoader;

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
        Renderer.RenderMesh(node.mesh, new Renderer.RenderDescription(RenderPipelines.DefaultPipeline, false, null));
        Renderer.RenderMesh(node.meshLine, new Renderer.RenderDescription(RenderPipelines.LinesPipeline, false, null));
        Renderer.RenderMesh(node.meshText, new Renderer.RenderDescription(RenderPipelines.TextPipeline, false, pass -> {
            if (FontLoader.getActiveFont() != null)
                pass.setUniform("Sampler0", FontLoader.getActiveFont().texture().getTextureView(), FontLoader.getActiveFont().texture().getSampler());
        }));
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
        public QuickMeshBuilder.Mesh meshText = null;

        boolean building = false;
        QuickMeshBuilder.Render2D meshBuilder;
        QuickMeshBuilder.Render2D meshBuilderLine;
        QuickMeshBuilder.Render2D meshBuilderText;

        protected void Begin() {
            building = true;
            meshBuilder = new QuickMeshBuilder.Render2D();
            meshBuilderLine = new QuickMeshBuilder.Render2D();
            meshBuilderText = new QuickMeshBuilder.Render2D();
            meshBuilder.BeginBuilding(false, false);
            meshBuilderLine.BeginBuilding(true, false);
            meshBuilderText.BeginBuilding(false, true);
        }

        protected void End() {
            if (!building)
                throw new RuntimeException("Not building gui mesh");
            building = false;
            mesh = meshBuilder.EndBuilding();
            meshLine = meshBuilderLine.EndBuilding();
            meshText = meshBuilderText.EndBuilding();
        }

        public void Quad(Vec2 a1, int width, int height, Color color) {
            meshBuilder.Rect(a1, width, height, color);
        }
        public void Line(Vec2 a1, Vec2 a2, Color color) {
            meshBuilderLine.Line(a1, a2, color);
        }
        public void Text(String text, Vec2 pos, float size, Color color) {
            if (FontLoader.getActiveFont() != null)
                meshBuilderText.Text(text, pos, size, color, FontLoader.getActiveFont());
        }
    }

}
