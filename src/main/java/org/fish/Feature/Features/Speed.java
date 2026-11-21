package org.fish.Feature.Features;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Mth;
import org.fish.Events.EventMove;
import org.fish.Feature.Feature;
import org.fish.Feature.Settings.DoubleSetting;
import org.fish.Gui.Tabs;
import org.joml.Vector3f;

import static org.fish.FishPlusPlusV2.mc;

public class Speed extends Feature {
    DoubleSetting speedSetting = new DoubleSetting("Speed", 1.0, 0.1, 5.0);
    public Speed() {
        super("Speed", Tabs.Movement);
        this.AddSetting(speedSetting);
    }

    @EventHandler
    public void onMove(EventMove event) {

        double yawRad = -Math.toRadians(mc.player.getYRot() + 180.0f);
        float forwardX = -Mth.sin(yawRad);
        float forwardZ = -Mth.cos(yawRad);

        float strafeX = forwardZ;
        float strafeZ = -forwardX;

        mc.player.setDeltaMovement(0, mc.player.getDeltaMovement().y, 0);

        Vector3f move = new Vector3f();

        float speed = speedSetting.getValue().floatValue();

        if (mc.player.input.keyPresses.forward())
            move.add(forwardX, 0.0f, forwardZ);
        if (mc.player.input.keyPresses.backward())
            move.add(-forwardX, 0.0f, -forwardZ);
        if (mc.player.input.keyPresses.left())
            move.add(strafeX, 0.0f, strafeZ);
        if (mc.player.input.keyPresses.right())
            move.add(-strafeX, 0.0f, -strafeZ);

        if (move.lengthSquared() > 0)
            move.normalize();

        move.x *= speed;
        move.z *= speed;

        event.delta.set(new Vector3f(move.x, event.delta.y, move.z));
    }
}
