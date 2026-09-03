package org.fish.Renderer;

import net.minecraft.client.gui.render.GuiRenderer;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class GuiRenderSystem {
    private final List<GuiRenderSystem.Node> strata = new ArrayList<>();
    private GuiRenderSystem.Node current;

    Matrix4f modelView;

    public void Render(Matrix4f modelView) {
        for (GuiRenderSystem.Node node : strata) {
            RenderNode(node, modelView);
        }
    }

    private void RenderNode(GuiRenderSystem.Node node, Matrix4f modelView) {
        for (GuiDrawCommand drawCommand : node.drawCommands) {
            Renderer.RenderMesh(drawCommand.mesh, modelView, false);
        }
    }

    public void NextStratum() {
        this.current = new GuiRenderSystem.Node();
        this.strata.add(this.current);
    }

    public void AddDrawCommand(GuiDrawCommand command) {
        this.current.AddDrawCommand(command);
    }

    public class Node {
        public List<GuiDrawCommand> drawCommands = new ArrayList<>();

        public void AddDrawCommand(GuiDrawCommand command) {
            this.drawCommands.add(command);
        }
    }

    public record GuiDrawCommand(QuickMeshBuilder.Mesh mesh) {

    }
}
