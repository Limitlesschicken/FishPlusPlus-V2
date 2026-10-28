package org.fish.mixin;

import net.minecraft.client.MouseHandler;
import org.fish.Events.EventMouseMove;
import org.fish.FishPlusPlusV2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onMove", at = @At("HEAD"))
    public void onMove(final long handle, final double xpos, final double ypos, final double xrel, final double yrel, CallbackInfo ci) {
        FishPlusPlusV2.EVENT_BUS.post(new EventMouseMove(xpos, ypos, xrel, yrel));
    }
}
