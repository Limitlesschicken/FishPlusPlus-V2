package org.fish.Events;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import meteordevelopment.orbit.ICancellable;

public class EventKey implements ICancellable {

    @Getter
    private InputConstants.Key key;
    @Getter
    private boolean in;

    private boolean cancel;

    public EventKey(InputConstants.Key key, boolean in) {
        this.key = key;
        this.in = in;
    }

    @Override
    public void setCancelled(boolean b) {
        cancel = b;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

}