package org.fish.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.fish.Events.EventKey;
import org.fish.FishPlusPlusV2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"))
    public void onKeyPress(final long handle, final int action, final KeyEvent event, CallbackInfo ci) {
        if (action == -1)
            return;

        InputConstants.Key key = InputConstants.getKey(event);
        FishPlusPlusV2.EVENT_BUS.post(new EventKey(key, action == 1));
    }
}
