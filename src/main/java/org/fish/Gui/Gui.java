package org.fish.Gui;

import lombok.Getter;
import lombok.Setter;
import meteordevelopment.orbit.EventHandler;
import org.fish.Events.EventKey;
import org.fish.Events.EventMouseMove;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.util.ArrayList;

import static org.fish.FishPlusPlusV2.mc;

public class Gui {
    private ArrayList<Tab> tabs = new ArrayList<>();

    private Region mainRegion;

    @Getter
    @Setter
    private static boolean open = false;

    public Gui() {
        FishPlusPlusV2.EVENT_BUS.subscribe(this);

        mainRegion = new Region(0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight());

        for (Tabs tabEnum : Tabs.values()) {
            Tab tab = new Tab(225 * (tabEnum.ordinal() + 1), 100, tabEnum.name());
            mainRegion.Add(tab);
        }
    }

    public void Render(GuiRenderSystem renderSystem) {
        if (!open)
            return;

        GuiRenderSystem.Node node = renderSystem.NextStratum();
        mainRegion.Render(node);

    }

    @EventHandler
    public void onKey(EventKey event) {
        if (event.in())
            Gui.setOpen(!Gui.isOpen());
    }

    @EventHandler
    public void onMouseMove(EventMouseMove event) {
        mainRegion.OnMouseHover(event.xpos(), event.ypos());
    }
}
