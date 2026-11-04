package org.fish.Feature;

import org.fish.Feature.Features.ESP;
import org.fish.Feature.Features.Flight;
import org.fish.Gui.Tabs;

import java.util.ArrayList;

public class FeatureHandler {
    public static ArrayList<Feature> features = new ArrayList<>();
    public FeatureHandler() {
        features.add(new Flight());
        features.add(new ESP());
    }
}
