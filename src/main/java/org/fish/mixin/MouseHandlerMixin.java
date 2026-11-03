package org.fish.mixin;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.fish.Events.EventMouseClick;
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

    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    public void onButton(final long handle, final MouseButtonInfo rawButtonInfo, final @MouseButtonInfo.Action int action, CallbackInfo ci) {
        FishPlusPlusV2.EVENT_BUS.post(new EventMouseClick(rawButtonInfo.input(), action == 1));

        //make sure mouse doesent interact with game while clickgui is open
        if (FishPlusPlusV2.GUI.isOpen())
            ci.cancel();
    }
    @Inject(method = "grabMouse", at = @At("HEAD"), cancellable = true)
    public void onGrabMouse(CallbackInfo ci) {
        //make sure mouse doesent interact with game while clickgui is open
        if (FishPlusPlusV2.GUI.isOpen())
            ci.cancel();
    }
}
