package org.fish.Gui;

import lombok.Getter;
import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Feature;
import org.fish.Feature.Setting;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class FeatureButton extends Region{
    private final String name;
    private final Feature feature;

    private final Region settingsBody;

    @Getter
    private float bodyHeight = 0.0f;

    private boolean openSettings = true;

    FeatureButton(float x, float y, String name, Feature feature) {
        super(x, y, 200, 30);
        this.name = name;
        this.feature = feature;
        this.bodyHeight = height;

        settingsBody = new Region(0, 0, width, 0);

        this.Add(settingsBody);

        for (Setting<?> setting : feature.getSettings()) {
            this.settingsBody.Add(setting.CreateButton(0, height));
        }
    }

    @Override
    public void Update() {
        settingsBody.height = 0;
        if (openSettings)
            settingsBody.height = settingsBody.children.size() * this.height;

        bodyHeight = this.height + settingsBody.height;

        super.Update();
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        Color color = StylingOptions.FeatureButtonColor;
        if (hovered)
            color = StylingOptions.FeatureButtonColorHovered;
        if (feature.isActive())
            color = StylingOptions.FeatureButtonColorActive;
        node.Quad(new Vec2(absoluteX, absoluteY), (int) width, (int) height, color);
        node.Text(name, new Vec2(absoluteX + StylingOptions.XTextSpacing, absoluteY + StylingOptions.YTextSpacing), 1.0f, Color.WHITE);

        if (this.openSettings)
            super.Render(node);
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY) && in) {
            if (button == 1)
                feature.Toggle();
            else if (button == 3)
                this.openSettings = !this.openSettings;
        }

        super.OnMouseClick(mouseX, mouseY, button, in);
    }

}
