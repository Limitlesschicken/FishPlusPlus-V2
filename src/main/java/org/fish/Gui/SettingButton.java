package org.fish.Gui;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Setting;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class SettingButton extends Region {
    protected Setting<?> setting;
    protected SettingButton(float x, float y, Setting<?> setting) {
        super(x, y, 200, 30);
        this.setting = setting;
    }

    protected void WriteNameText(GuiRenderSystem.Node node) {
        node.Text(setting.getName(), new Vec2(absoluteX + StylingOptions.XTextSpacing, absoluteY + StylingOptions.YTextSpacing), 1.0f, Color.WHITE);
    }

    protected void WriteButtonBackground(GuiRenderSystem.Node node) {
        node.Quad(new Vec2(this.absoluteX, this.absoluteY), (int) this.width, (int) this.height, StylingOptions.FeatureButtonColor);
    }
}
