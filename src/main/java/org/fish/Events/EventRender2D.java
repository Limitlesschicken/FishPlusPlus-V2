package org.fish.Events;

import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.QuickMeshBuilder;

public class EventRender2D {
    public GuiRenderSystem.Node node;
    public float tickDelta;

    public EventRender2D(GuiRenderSystem.Node node, float tickDelta) {
        this.node = node;
        this.tickDelta = tickDelta;
    }
}
