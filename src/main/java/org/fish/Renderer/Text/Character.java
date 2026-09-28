package org.fish.Renderer.Text;

import net.minecraft.world.phys.Vec2;

public record Character(Vec2 size, Vec2 bearing, int Advance, int Ascender, float tx, float tw) {
}
