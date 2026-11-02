package org.fish.Events;

import net.minecraft.client.DeltaTracker;
import org.fish.Renderer.WorldRenderSystem;

public record EventRender3D(WorldRenderSystem renderer, float tickDelta) {
}
