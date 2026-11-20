package org.fish.Gui.Settings;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Setting;
import org.fish.Feature.Settings.DoubleSetting;
import org.fish.Gui.SettingButton;
import org.fish.Gui.StylingOptions;
import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.Text.FontLoader;

import java.awt.*;
import java.text.DecimalFormat;

public class SliderButton extends SettingButton<Double> {

    private boolean editing = false;

    public SliderButton(float x, float y, Setting<Double> setting) {
        super(x, y, setting);
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {
        this.WriteNameText(node);
        this.WriteButtonBackground(node);

        DoubleSetting doubleSetting = (DoubleSetting) this.setting;
        double sliderProgress = (doubleSetting.getValue() - doubleSetting.getMinValue()) / (doubleSetting.getMaxValue() - doubleSetting.getMinValue());
        node.Quad(new Vec2(this.absoluteX, this.absoluteY), (int) (sliderProgress * this.width), (int) this.height, StylingOptions.FeatureButtonColorActive);

        String textValue = new DecimalFormat("0.0").format(doubleSetting.getValue());
        node.Text(textValue, new Vec2(this.absoluteX + width - FontLoader.GetLength(textValue, 1.0f) - 5.0f, this.absoluteY), 1.0f, Color.WHITE);
    }

    void CalculateSliderValue(double mouseX) {
        double sliderMouseCoord = mouseX - this.absoluteX;
        double newProgress = sliderMouseCoord / (this.absoluteX + this.width - this.absoluteX);

        DoubleSetting doubleSetting = (DoubleSetting) this.setting;
        double newValue = newProgress * (doubleSetting.getMaxValue() - doubleSetting.getMinValue()) + doubleSetting.getMinValue();
        newValue = Mth.clamp(newValue, doubleSetting.getMinValue(), doubleSetting.getMaxValue());
        doubleSetting.setValue(newValue);
    }

    @Override
    public void OnMouseMove(double mouseX, double mouseY) {
        super.OnMouseMove(mouseX, mouseY);

        if (editing)
            CalculateSliderValue(mouseX);
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY) && in) {
            if (button != 1)
                return;

            editing = true;
            CalculateSliderValue(mouseX);
        }
        if (!in)
            editing = false;

        super.OnMouseClick(mouseX, mouseY, button, in);
    }
}
