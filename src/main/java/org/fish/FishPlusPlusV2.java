package org.fish;

import net.fabricmc.api.ClientModInitializer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import org.fish.Renderer.RenderPipelines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FishPlusPlusV2 implements ClientModInitializer {
	public static final String MOD_ID = "fishplusplusv2";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Minecraft mc;

	@Override
	public void onInitializeClient() {
		mc = Minecraft.getInstance();
		RenderPipelines.init();


	}




	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
