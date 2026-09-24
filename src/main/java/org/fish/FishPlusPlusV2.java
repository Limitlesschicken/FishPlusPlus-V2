package org.fish;

import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import org.fish.Events.EventRender2D;
import org.fish.Events.EventRender3D;
import org.fish.Renderer.RenderPipelines;
import org.fish.Utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.lang.invoke.MethodHandles;

public class FishPlusPlusV2 implements ClientModInitializer {
	public static final String MOD_ID = "fishplusplusv2";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Minecraft mc;

	public static IEventBus EVENT_BUS = new EventBus();

	@Override
	public void onInitializeClient() {
		mc = Minecraft.getInstance();

		EVENT_BUS.registerLambdaFactory("org.fish", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

		RenderPipelines.init();

		EVENT_BUS.subscribe(this);
	}

	@EventHandler
	public void onRender2D(EventRender2D event) {
		event.node.Quad(new Vec2(200.0f, 200.0f), 100, 100, Color.BLUE);
	}

	@EventHandler
	public void onRender3D(EventRender3D event) {

		for (Entity entity : EntityUtils.GetEntites()) {
			if (entity == mc.player)
				continue;

			event.renderer.Box(entity, event.tickDelta, new Color(255, 100, 100, 50), new Color(255, 100, 100, 255));
		}


		//event.renderer.Box(new Vec3(0, 0, 0), new Vec3(1, 1, 1), new Color(255, 100, 100, 50), new Color(255, 100, 100, 255));
	}



	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
