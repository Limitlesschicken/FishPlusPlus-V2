package org.fish.Gui;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import lombok.Setter;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.KeyMapping;
import org.fish.Events.EventKey;
import org.fish.Events.EventMouseClick;
import org.fish.Events.EventMouseMove;
import org.fish.Feature.Feature;
import org.fish.Feature.FeatureHandler;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.util.ArrayList;

import static org.fish.FishPlusPlusV2.*;

public class Gui {
    private final Region mainRegion;

    @Getter
    @Setter
    private boolean open = false;

    public Gui() {
        FishPlusPlusV2.EVENT_BUS.subscribe(this);

        //region that just has everything under it like tabs (maybe some other stuff if i add it)
        //but probably not hud stuff
        //Nothing visual about it so the size and position does not matter
        mainRegion = new Region(0, 0, 0, 0);

        for (Tabs tabEnum : Tabs.values()) {
            Tab tab = new Tab(225 * (tabEnum.ordinal() + 1), 100, tabEnum.name());
            int i = 1;
            for (Feature feature : FeatureHandler.features) {
                if (!tabEnum.equals(feature.tab))
                    continue;

                FeatureButton featureButton = new FeatureButton(tab.x, tab.y + 30 * i, feature.name, feature);
                tab.Add(featureButton);

                i++;
            }
            mainRegion.Add(tab);
        }
    }

    public void Render(GuiRenderSystem renderSystem) {
        if (!open)
            return;

        for (Region childRegions : mainRegion.children) {
            GuiRenderSystem.Node node = renderSystem.NextStratum();
            childRegions.Render(node);
        }

    }

    @EventHandler
    public void onKey(EventKey event) {
        if (!event.isIn())
            return;

        boolean closed = false;

        //esc always closes clickGui but never opens it
        if (event.getKey().getValue() == InputConstants.KEY_ESCAPE && GUI.isOpen()) {
            this.setOpen(false);
            event.setCancelled(true);
            closed = true;
        }

        if (event.getKey().getValue() == InputConstants.KEY_RSHIFT) {
            closed = this.isOpen();
            this.setOpen(!this.isOpen());
            event.setCancelled(true);

        }

        if (event.isCancelled()) {
            if (closed && mc.gui.screen() == null)
                mc.mouseHandler.grabMouse();
            else {
                KeyMapping.releaseAll();
                mc.mouseHandler.releaseMouse();
            }
        }

        if (GUI.isOpen())
            event.setCancelled(true);
    }

    @EventHandler
    public void onMouseMove(EventMouseMove event) {
        mainRegion.OnMouseHover(event.xpos(), event.ypos());
    }

    @EventHandler
    public void onMouseClick(EventMouseClick event) {
        mainRegion.OnMouseClick(mc.mouseHandler.xpos(), mc.mouseHandler.ypos(), event.button(), event.in());
    }
}
