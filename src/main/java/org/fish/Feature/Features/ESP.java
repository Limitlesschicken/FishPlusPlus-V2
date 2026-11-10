package org.fish.Feature.Features;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.entity.Entity;
import org.fish.Events.EventRender3D;
import org.fish.Feature.Feature;
import org.fish.Feature.Settings.ToggleSetting;
import org.fish.Gui.Tabs;
import org.fish.Utils.EntityUtils;

import java.awt.*;

import static org.fish.FishPlusPlusV2.mc;

public class ESP extends Feature {
    ToggleSetting testSetting = new ToggleSetting("Test Setting", true);
    public ESP() {
        super("ESP", Tabs.Visual);
        this.AddSetting(testSetting);
    }

    @EventHandler
    public void OnRender3D(EventRender3D event) {
        for (Entity entity : EntityUtils.GetEntites()) {
            if (entity == mc.player)
                continue;

            event.renderer().Box(entity, event.tickDelta(), new Color(255, 100, 100, 50), new Color(255, 100, 100, 255));

        }

    }
}
