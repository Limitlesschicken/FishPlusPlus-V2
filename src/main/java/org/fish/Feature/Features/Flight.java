package org.fish.Feature.Features;

import meteordevelopment.orbit.EventHandler;
import org.fish.Events.EventTick;
import org.fish.Feature.Feature;
import org.fish.Gui.Tabs;

import static org.fish.FishPlusPlusV2.mc;

public class Flight extends Feature {
    public Flight() {
        super("Flight", Tabs.Test);
    }

    @EventHandler
    public void onTick(EventTick event) {
        if (mc.player == null)
            return;

        mc.player.setPos(mc.player.position().x, 200, mc.player.position().z);
    }
}
