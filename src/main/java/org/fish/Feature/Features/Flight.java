package org.fish.Feature.Features;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.phys.Vec3;
import org.fish.Events.EventMove;
import org.fish.Events.EventTick;
import org.fish.Feature.Feature;
import org.fish.FishPlusPlusV2;
import org.fish.Gui.Tabs;
import org.joml.Vector3f;

import static org.fish.FishPlusPlusV2.mc;

public class Flight extends Feature {
    public Flight() {
        super("Flight", Tabs.Movement);
    }

    @EventHandler
    public void onMove(EventMove event) {
        event.delta.set(0, 0, 0);
        mc.player.setDeltaMovement(0, 0, 0);
        //mc.player.setPos(mc.player.position().x, 200, mc.player.position().z);
    }
}
