package org.fish.Gui.Settings;

import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Setting;
import org.fish.Feature.Settings.ToggleSetting;
import org.fish.Gui.SettingButton;
import org.fish.Gui.StylingOptions;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class ToggleButton extends SettingButton {
    public ToggleButton(float x, float y, Setting<Boolean> setting) {
        super(x, y, setting);
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        this.WriteNameText(node);
        this.WriteButtonBackground(node);
    }
}
