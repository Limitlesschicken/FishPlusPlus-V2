package org.fish.Feature;

import lombok.Getter;
import org.fish.FishPlusPlusV2;
import org.fish.Gui.Tabs;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    public String name;
    public Tabs tab;

    @Getter
    private List<Setting<?>> settings = new ArrayList<>();

    @Getter
    private boolean active = false;

    public Feature(String name, Tabs tab) {
        this.name = name;
        this.tab = tab;
    }

    public void Toggle() {
        active = !active;
        if (active) {
            FishPlusPlusV2.EVENT_BUS.subscribe(this);
        } else {
            FishPlusPlusV2.EVENT_BUS.unsubscribe(this);
        }
    }

    public void AddSetting(Setting<?> setting) {
        settings.add(setting);
    }
}
