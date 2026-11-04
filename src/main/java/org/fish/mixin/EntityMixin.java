package org.fish.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.sun.jna.platform.linux.Udev;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.fish.Events.EventMove;
import org.fish.FishPlusPlusV2;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static org.fish.FishPlusPlusV2.mc;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyVariable(method = "move", at = @At("HEAD"), argsOnly = true, index = 2)
    public Vec3 onMove(Vec3 delta, @Local(argsOnly = true, ordinal = 0) MoverType moverType) {
        if (((Object) this) != mc.player)
            return delta;

        Vector3f newVec = delta.toVector3f();
        EventMove eventMove = new EventMove(moverType, newVec);
        FishPlusPlusV2.EVENT_BUS.post(eventMove);
        return new Vec3(newVec.x, newVec.y, newVec.z);
    }
}
