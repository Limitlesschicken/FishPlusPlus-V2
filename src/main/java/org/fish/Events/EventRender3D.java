package org.fish.Events;

import net.minecraft.client.DeltaTracker;
import org.fish.Renderer.WorldRenderSystem;

public class EventRender3D {
    public WorldRenderSystem renderer;
    public float tickDelta;

    public EventRender3D(WorldRenderSystem renderSystem, float tickDelta) {
        this.renderer = renderSystem;
        this.tickDelta = tickDelta;
    }
}
