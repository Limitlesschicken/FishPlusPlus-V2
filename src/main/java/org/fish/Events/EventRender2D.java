package org.fish.Events;

import org.fish.Renderer.GuiRenderSystem;
import org.fish.Renderer.QuickMeshBuilder;

public record EventRender2D(GuiRenderSystem.Node node, float tickDelta) {
}
