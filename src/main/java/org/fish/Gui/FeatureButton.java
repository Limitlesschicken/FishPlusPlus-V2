package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Feature;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class FeatureButton extends Region{
    private final String name;
    private final Feature feature;
    FeatureButton(float x, float y, String name, Feature feature) {
        super(x, y, 200, 30);
        this.name = name;
        this.feature = feature;
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        Color color = new Color(60, 60, 60, 255);
        if (hovered)
            color = new Color(70, 70, 70, 255);
        if (feature.isActive())
            color = new Color(255, 100, 100, 255);
        node.Quad(new Vec2(x, y), (int) width, (int) height, color);
        node.Text(name, new Vec2(x, y), 1.0f, Color.WHITE);
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY) && in) {
            feature.Toggle();
        }
    }

}
