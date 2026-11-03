package org.fish.Gui;

import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.util.ArrayList;

public class Region {
    protected ArrayList<Region> children = new ArrayList<>();
    protected Region parent;

    float x;
    float y;
    float width;
    float height;

    boolean hovered = false;

    Region(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void Add(Region child) {
        if (child.parent != null)
            child.parent.Remove(child);

        children.add(child);
        child.parent = this;
    }

    void Remove(Region child) {
        if (children.remove(child))
            child.parent = null;
    }

    public void Render(GuiRenderSystem.Node node) {
        this.children.forEach((region -> region.Render(node)));
    }

    public boolean Bounds(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void OnMouseHover(double mouseX, double mouseY) {
        hovered = Bounds(mouseX, mouseY);
        this.children.forEach(region -> {region.OnMouseHover(mouseX, mouseY);});
    }

    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        this.children.forEach(region -> {region.OnMouseClick(mouseX, mouseY, button, in);});
    }
}
