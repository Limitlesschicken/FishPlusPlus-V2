package org.fish.Gui;

import org.fish.Feature.Setting;

public class SettingButton extends Region {
    Setting<?> setting;
    protected SettingButton(Setting<?> setting) {
        super();
        this.setting = setting;
    }
}
