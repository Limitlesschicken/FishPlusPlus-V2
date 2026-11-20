package org.fish.Feature.Settings;

import org.fish.Feature.Setting;
import org.fish.Gui.Settings.SliderButton;

public class DoubleSetting extends Setting<Double> {

    double minValue;
    double maxValue;

    public DoubleSetting(String name, Double defaultValue, double minValue, double maxValue) {
        super(name, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public SliderButton CreateButton(float x, float y) {
        return new SliderButton(x, y, this);
    }
}
