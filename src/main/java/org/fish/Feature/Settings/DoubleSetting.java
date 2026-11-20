package org.fish.Feature.Settings;

import lombok.Getter;
import org.fish.Feature.Setting;
import org.fish.Gui.Settings.SliderButton;

public class DoubleSetting extends Setting<Double> {

    @Getter
    private double minValue;
    @Getter
    private double maxValue;

    public DoubleSetting(String name, Double defaultValue, double minValue, double maxValue) {
        super(name, defaultValue);
        if (minValue >= maxValue) {
            throw new ArithmeticException("Min value must be/not equal to below the max value");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public SliderButton CreateButton(float x, float y) {
        return new SliderButton(x, y, this);
    }
}
