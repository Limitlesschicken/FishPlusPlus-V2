package org.fish.Feature.Settings;

import org.fish.Feature.Setting;
import org.fish.Gui.Region;
import org.fish.Gui.Settings.ToggleButton;

public class ToggleSetting extends Setting<Boolean> {
    public ToggleSetting(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    public ToggleButton CreateButton() {
        return new ToggleButton(this);
    }
}
