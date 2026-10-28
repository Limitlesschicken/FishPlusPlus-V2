package org.fish.Events;

import com.mojang.blaze3d.platform.InputConstants;

public record EventKey(InputConstants.Key key, boolean in) {
}
