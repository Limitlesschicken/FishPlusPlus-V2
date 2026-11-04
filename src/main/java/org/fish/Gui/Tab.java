package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class Tab extends Region {
    private String name;

    Tab(float x, float y, String name) {
        super(x, y, 200, 30);
        this.name = name;
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        node.Quad(new Vec2(x, y), (int) width, (int) height, new Color(50, 50, 50, 255));
        node.Text(name, new Vec2(x, y), 1, Color.WHITE);

        super.Render(node);
    }
}
