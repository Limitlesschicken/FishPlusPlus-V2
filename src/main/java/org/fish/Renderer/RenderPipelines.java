package org.fish.Renderer;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.PolygonMode;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.BindGroupLayouts;
import org.fish.FishPlusPlusV2;

import java.util.ArrayList;
import java.util.List;


public class RenderPipelines {

    public static List<RenderPipeline> RenderPipelines = new ArrayList<>();

    public static RenderPipeline.Snippet DefaultSnippet;
    public static RenderPipeline DefaultPipeline;

    //add pipelines to an array for storage
    public static RenderPipeline register(RenderPipeline pipeline) {
        RenderPipelines.add(pipeline);
        return pipeline;
    }

    public static void init() {
        //just a pipeline for basic 3d rendering. position and color
        DefaultSnippet = RenderPipeline.builder()
                .withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)
                .withCull(false)
                .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
                .withPolygonMode(PolygonMode.FILL)
                .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
                .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .buildSnippet();

        DefaultPipeline = register(
                RenderPipeline.builder(DefaultSnippet)
                        .withLocation(FishPlusPlusV2.id("pipeline/default"))
                        .withVertexShader(FishPlusPlusV2.id("default"))
                        .withFragmentShader(FishPlusPlusV2.id("default"))
                        .build()
                );
    }
}
