package org.fish;

import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import org.fish.Events.EventKey;
import org.fish.Events.EventRender2D;
import org.fish.Events.EventRender3D;
import org.fish.Feature.FeatureHandler;
import org.fish.Gui.Gui;
import org.fish.Renderer.RenderPipelines;
import org.fish.Utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.lang.invoke.MethodHandles;

public class FishPlusPlusV2 implements ClientModInitializer {
	public static final String MOD_ID = "fishplusplusv2";
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Minecraft mc;

	public static Gui GUI;
	public static FeatureHandler FEATURE_HANDLER;

	public static IEventBus EVENT_BUS = new EventBus();

	@Override
	public void onInitializeClient() {
		mc = Minecraft.getInstance();

		EVENT_BUS.registerLambdaFactory("org.fish", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

		RenderPipelines.init();

		EVENT_BUS.subscribe(this);
	}
}
