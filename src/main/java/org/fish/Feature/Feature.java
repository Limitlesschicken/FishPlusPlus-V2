package org.fish.Feature;

import org.fish.FishPlusPlusV2;
import org.fish.Gui.Tabs;

public class Feature {
    public String name;
    public Tabs tab;

    boolean active = false;

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
}
