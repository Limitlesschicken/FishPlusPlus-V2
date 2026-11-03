package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class Tab extends Region {
    private String name;

    Color color = Color.GREEN;
    boolean changedColor = false;

    Tab(float x, float y, String name) {
        super(x, y, 125, 50);
        this.name = name;
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        if (hovered && !changedColor) {
            color = Color.BLUE;
            changedColor = true;
        }
        node.Quad(new Vec2(x, y), (int) width, (int) height, color);
        node.Text(name, new Vec2(x, y), 1, Color.WHITE);
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY))
            color = Color.RED;
    }
}
