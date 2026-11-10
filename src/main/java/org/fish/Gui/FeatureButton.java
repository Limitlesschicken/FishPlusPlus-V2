package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Feature;
import org.fish.Feature.Setting;
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

        for (Setting<?> setting : feature.getSettings()) {
            this.Add(setting.CreateButton(width, 0));
        }
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        Color color = new Color(60, 60, 60, 255);
        if (hovered)
            color = new Color(70, 70, 70, 255);
        if (feature.isActive())
            color = new Color(255, 100, 100, 255);
        node.Quad(new Vec2(absoluteX, absoluteY), (int) width, (int) height, color);
        node.Text(name, new Vec2(absoluteX, absoluteY), 1.0f, Color.WHITE);
        super.Render(node);
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY) && in) {
            feature.Toggle();
        }

        super.OnMouseClick(mouseX, mouseY, button, in);
    }

}
