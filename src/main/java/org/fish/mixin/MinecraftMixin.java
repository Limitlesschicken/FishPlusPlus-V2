package org.fish.mixin;

import net.minecraft.client.Minecraft;
import org.fish.Renderer.Renderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.fish.FishPlusPlusV2.mc;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void InitMinecraft(CallbackInfo ci) {
        Renderer.Init();
    }
}
