package org.fish.Gui.Settings;

import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Setting;
import org.fish.Gui.SettingButton;
import org.fish.Gui.StylingOptions;
import org.fish.Renderer.GuiRenderSystem;

public class SliderButton extends SettingButton<Double> {

    public SliderButton(float x, float y, Setting<Double> setting) {
        super(x, y, setting);
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        this.WriteNameText(node);
        this.WriteButtonBackground(node);
    }
}
