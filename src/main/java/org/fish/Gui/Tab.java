package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class Tab extends Region {
    private String name;

    Tab(float x, float y, String name) {
        super(x, y, 125, 50);
        this.name = name;
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        node.Quad(new Vec2(x, y), (int) width, (int) height, hovered ? Color.BLUE : Color.GREEN);
        node.Text(name, new Vec2(x, y), 1, Color.WHITE);
    }
}
