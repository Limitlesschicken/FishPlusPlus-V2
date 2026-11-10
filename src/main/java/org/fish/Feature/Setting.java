package org.fish.Feature;

import lombok.Getter;
import lombok.Setter;
import org.fish.Gui.Settings.ToggleButton;

public abstract class Setting<T> {
    @Getter
    @Setter
    private T value;

    @Getter
    private final T defaultValue;

    @Getter
    private final String name;

    public Setting(String name, T defaultValue) {
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.name = name;
    }

   public abstract ToggleButton CreateButton();
}
