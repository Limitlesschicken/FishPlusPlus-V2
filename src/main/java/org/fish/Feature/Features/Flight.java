package org.fish.Feature.Features;

import com.mojang.blaze3d.platform.InputConstants;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Mth;
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

        double yawRad = -Math.toRadians(mc.player.getYRot() + 180.0f);
        float forwardX = -Mth.sin(yawRad);
        float forwardZ = -Mth.cos(yawRad);

        float strafeX = forwardZ;
        float strafeZ = -forwardX;

        mc.player.setDeltaMovement(0, 0, 0);

        Vector3f offset = new Vector3f();
        Vector3f move = new Vector3f();

        float speed = 1.0f;

        if (mc.player.input.keyPresses.forward())
            move.add(forwardX, 0.0f, forwardZ);
        if (mc.player.input.keyPresses.backward())
            move.add(-forwardX, 0.0f, -forwardZ);
        if (mc.player.input.keyPresses.left())
            move.add(strafeX, 0.0f, strafeZ);
        if (mc.player.input.keyPresses.right())
            move.add(-strafeX, 0.0f, -strafeZ);

        if (mc.player.input.keyPresses.jump())
            offset.y += speed;
        if (mc.player.input.keyPresses.shift())
            offset.y += -speed;

        if (move.lengthSquared() > 0)
            move.normalize();

        offset.x = move.x * speed;
        offset.z = move.z * speed;

        event.delta.set(offset);
    }
}
