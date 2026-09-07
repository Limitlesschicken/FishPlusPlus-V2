package org.fish.Utils;

import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.Entity;
import org.fish.mixin.accessor.ILevelMixin;

import java.util.ArrayList;
import java.util.List;

import static org.fish.FishPlusPlusV2.mc;

@UtilityClass
public class EntityUtils {
    public List<Entity> GetEntites() {
        if (mc.level == null)
            return new ArrayList<>();

        List<Entity> list = new ArrayList<>();
        for (Entity entity : ((ILevelMixin) mc.level).invokeGetEntities().getAll()) {
            list.add(entity);
        }
        return list;
    }
}
