package org.fish.Gui;

import org.fish.Feature.Setting;

public class SettingButton extends Region {
    Setting<?> setting;
    protected SettingButton(float x, float y, Setting<?> setting) {
        super(x, y, 200, 30);
        this.setting = setting;
    }
}
