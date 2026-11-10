package org.fish.Gui.Settings;

import org.fish.Feature.Setting;
import org.fish.Feature.Settings.ToggleSetting;
import org.fish.Gui.SettingButton;

public class ToggleButton extends SettingButton {
    public ToggleButton(Setting<Boolean> setting) {
        super(setting);
    }
}
