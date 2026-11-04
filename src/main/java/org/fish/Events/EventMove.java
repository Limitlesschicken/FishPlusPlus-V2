package org.fish.Events;

import lombok.Getter;
import lombok.Setter;
import meteordevelopment.orbit.EventBus;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class EventMove {
    public Vector3f delta;

    @Getter
    private MoverType moverType;

    public EventMove(MoverType moverType, Vector3f delta) {
        this.delta = delta;
        this.moverType = moverType;
    }
}
