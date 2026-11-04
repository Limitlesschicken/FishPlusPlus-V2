package org.fish.mixin;

import net.minecraft.client.Minecraft;
import org.fish.Events.EventTick;
import org.fish.Feature.FeatureHandler;
import org.fish.FishPlusPlusV2;
import org.fish.Gui.Gui;
import org.fish.Renderer.Renderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.fish.FishPlusPlusV2.FEATURE_HANDLER;
import static org.fish.FishPlusPlusV2.GUI;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void InitMinecraft(CallbackInfo ci) {
        Renderer.Init();
        FEATURE_HANDLER = new FeatureHandler();
        GUI = new Gui();

    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTick(CallbackInfo ci) {
        EventTick eventTick = new EventTick();
        FishPlusPlusV2.EVENT_BUS.post(eventTick);
    }
}
