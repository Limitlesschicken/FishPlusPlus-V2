package org.fish.Renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.pipeline.*;
import net.minecraft.client.renderer.BindGroupLayouts;
import org.fish.FishPlusPlusV2;

import java.util.ArrayList;
import java.util.List;


public class RenderPipelines {

    public static List<RenderPipeline> RenderPipelines = new ArrayList<>();

    public static RenderPipeline.Snippet DefaultSnippet;
    public static RenderPipeline.Snippet TextureSnippet;

    public static RenderPipeline DefaultPipeline;
    public static RenderPipeline LinesPipeline;
    public static RenderPipeline TextPipeline;

    //add pipelines to an array for storage
    public static RenderPipeline register(RenderPipeline pipeline) {
        RenderPipelines.add(pipeline);
        return pipeline;
    }

    public static void init() {
        //just a pipeline for basic 3d rendering. position and color
        DefaultSnippet = RenderPipeline.builder()
                .withBindGroupLayout(BindGroupLayouts.PROJECTION)
                .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                .withCull(false)
                .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
                .withPolygonMode(PolygonMode.FILL)
                .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .buildSnippet();

        TextureSnippet = RenderPipeline.builder()
                .withBindGroupLayout(BindGroupLayouts.PROJECTION)
                .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
                .withCull(false)
                .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR)
                .withPolygonMode(PolygonMode.FILL)
                .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .buildSnippet();

        DefaultPipeline = register(
                RenderPipeline.builder(DefaultSnippet)
                        .withLocation(FishPlusPlusV2.id("pipeline/default"))
                        .withVertexShader(FishPlusPlusV2.id("default"))
                        .withFragmentShader(FishPlusPlusV2.id("default"))
                        .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
                        .build()
                );

        LinesPipeline = register(
                RenderPipeline.builder(DefaultSnippet)
                        .withLocation(FishPlusPlusV2.id("pipeline/lines"))
                        .withVertexShader(FishPlusPlusV2.id("default"))
                        .withFragmentShader(FishPlusPlusV2.id("default"))
                        .withPrimitiveTopology(PrimitiveTopology.DEBUG_LINES)
                        .build()
        );
        TextPipeline = register(
                RenderPipeline.builder(TextureSnippet)
                        .withLocation(FishPlusPlusV2.id("pipeline/text"))
                        .withVertexShader(FishPlusPlusV2.id("text"))
                        .withFragmentShader(FishPlusPlusV2.id("text"))
                        .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
                        .build()
        );
    }
}
