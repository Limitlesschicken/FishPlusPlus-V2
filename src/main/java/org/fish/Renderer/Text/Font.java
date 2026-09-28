package org.fish.Renderer.Text;

import net.minecraft.client.renderer.texture.DynamicTexture;

import java.util.HashMap;

public record Font(HashMap<java.lang.Character, Character> characters, DynamicTexture texture, int size, int atlasWidth, int atlasHeight) {
}
