package org.fish.mixin;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.fish.FishPlusPlusV2;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    /*
    Use this to get rid of fabric api dependency.
    Get rid of "implements ClientModInitializer" and "@Override".
    make onInitializeClient static.
    and get rid of the import for ClientModInitializer


    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        FishPlusPlusV2.onInitializeClient();
    }

     */
}
