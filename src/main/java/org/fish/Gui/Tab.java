package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class Tab extends Region {
    private String name;

    private boolean dragging;
    private Vec2 draggingPos;

    Tab(float x, float y, String name) {
        super(x, y, 200, 30);
        this.name = name;
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {

        node.Quad(new Vec2(absoluteX, absoluteY), (int) width, (int) height, new Color(50, 50, 50, 255));
        node.Text(name, new Vec2(absoluteX, absoluteY), 1, Color.WHITE);

        super.Render(node);
    }

    @Override
    public void OnMouseHover(double mouseX, double mouseY) {
        super.OnMouseHover(mouseX, mouseY);

        if (dragging) {
            this.x = (float) mouseX + draggingPos.x;
            this.y = (float) mouseY + draggingPos.y;
        }
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {

        if (Bounds(mouseX, mouseY) && in) {
            dragging = true;
            draggingPos = new Vec2((float) (absoluteX - mouseX), (float) (absoluteY - mouseY));
        }
        if (!in)
            dragging = false;

        super.OnMouseClick(mouseX, mouseY, button, in);
    }
}
