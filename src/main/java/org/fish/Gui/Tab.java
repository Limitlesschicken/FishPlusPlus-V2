package org.fish.Gui;

import net.minecraft.world.phys.Vec2;
import org.fish.Feature.Feature;
import org.fish.Feature.FeatureHandler;
import org.fish.FishPlusPlusV2;
import org.fish.Renderer.GuiRenderSystem;

import java.awt.*;

public class Tab extends Region {
    private String name;

    private boolean dragging;
    private Vec2 draggingPos;

    //Setting to true moves the tab to the front of the list in Gui. Turns off 1 frame later
    public boolean moveToFront = false;

    Tab(float x, float y, String name) {
        super(x, y, 200, 30);
        this.name = name;
    }

    public void AddTabs(Tabs currentTab) {
        int i = 1;
        for (Feature feature : FeatureHandler.features) {
            if (!currentTab.equals(feature.tab))
                continue;

            //Yea they get set assuming all feature buttons are all closed i think thats ok
            FeatureButton featureButton = new FeatureButton(0, 30 * i, feature.name, feature);
            this.Add(featureButton);

            i++;
        }
    }

    private void SetFeaturePositions() {
        float currentY = this.height;

        /*
            I wouldent change the absoluteY like this normally but heres why i do it:
            FeatureButton::Update sets the body height to the correct values upon closing and opening a tab.
            Region::Update then sets the absoluteY BEFORE the y has changed so it remains the same.
            Then this function sets the y. The absoluteY which is used for rendering remains the same as it was already updated earlier.
            this leads to a 1 frame delay in the tab moving down upon closing and opening
        */
        float currentAbsoluteY = this.absoluteY + currentY;

        for (Region button : this.children) {
            if (!(button instanceof FeatureButton featureButton))
                continue;

            featureButton.y = currentY;
            featureButton.absoluteY = currentAbsoluteY;
            currentY += featureButton.getBodyHeight() + featureButton.height;
            currentAbsoluteY += featureButton.getBodyHeight() + featureButton.height;
        }
    }

    @Override
    public void Update() {
        super.Update();

        SetFeaturePositions();
    }

    @Override
    public void Render(GuiRenderSystem.Node node) {

        node.Quad(new Vec2(absoluteX, absoluteY), (int) width, (int) height, StylingOptions.TabColor);
        node.Text(name, new Vec2(absoluteX + StylingOptions.XTextSpacing, absoluteY + StylingOptions.YTextSpacing), 1, Color.WHITE);

        super.Render(node);
    }

    @Override
    public void OnMouseMove(double mouseX, double mouseY) {
        super.OnMouseMove(mouseX, mouseY);

        if (dragging) {
            this.x = (float) mouseX + draggingPos.x;
            this.y = (float) mouseY + draggingPos.y;
        }
    }

    @Override
    public void OnMouseClick(double mouseX, double mouseY, int button, boolean in) {
        if (Bounds(mouseX, mouseY) && in && !FishPlusPlusV2.GUI.draggingTab) {
            dragging = true;
            draggingPos = new Vec2((float) (absoluteX - mouseX), (float) (absoluteY - mouseY));
            moveToFront = true;
            FishPlusPlusV2.GUI.draggingTab = true;
        }
        
        if (!in) {
            dragging = false;
            FishPlusPlusV2.GUI.draggingTab = false;
        }

        super.OnMouseClick(mouseX, mouseY, button, in);
    }
}
