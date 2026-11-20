package org.fish.Gui.Settings;

import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Setting;
import org.fish.Feature.Settings.ToggleSetting;
import org.fish.Gui.SettingButton;
import org.fish.Gui.StylingOptions;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class ToggleButton extends SettingButton<Boolean> {
    public ToggleButton(float x, float y, Setting<Boolean> setting) {
        super(x, y, setting);
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        this.WriteNameText(node);

        if (!this.setting.getValue())
            this.WriteButtonBackground(node);
        else
            node.Quad(new Vec2(this.absoluteX, this.absoluteY), (int) this.width, (int) this.height, StylingOptions.FeatureButtonColorActive);
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY) && in) {
            if (button == 1)
                ((ToggleSetting)this.setting).Toggle();
        }

        super.OnMouseClick(mouseX, mouseY, button, in);
    }
}
