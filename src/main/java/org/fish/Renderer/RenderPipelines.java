package org.fish.Renderer;

import com.mojang.blaze3d.PrimitiveTopology;
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
                .buildSnippet();

        DefaultPipeline = register(
                RenderPipeline.builder(DefaultSnippet)
                        .withLocation(FishPlusPlusV2.id("pipeline/default"))
                        .withVertexShader(FishPlusPlusV2.id("default"))
                        .withFragmentShader(FishPlusPlusV2.id("default"))
                        .build()
                );
    }

    /*
    public static void compile(ResourceProvider resourceProvider) {
        FishPlusPlusV2.LOGGER.info("Precompiling FishPlusPlus pipelines");
        GpuDevice gpuDevice = RenderSystem.getDevice();
        ResourceManager resourceManager = mc.getResourceManager();

        LOGGER.info("Resource packs: {}", resourceManager.listPacks().toList());

        Identifier vertex = FishPlusPlusV2.id("shaders/default.vsh");
        Identifier fragment = FishPlusPlusV2.id("shaders/default.fsh");

        LOGGER.info("Vertex resource: {}", resourceManager.getResource(vertex));
        LOGGER.info("Fragment resource: {}", resourceManager.getResource(fragment));

        for(RenderPipeline pipeline : RenderPipelines) {
            ShaderSource shaderSource = (id, type) -> {
                Identifier location = type.idConverter().idToFile(id);
                Optional<Resource> resourceOptional = resourceManager.getResource(location);
                if (resourceOptional.isEmpty()) {
                    LOGGER.error("Coudln't preload {} shader {}: no resource", new Object[]{type, id});
                    return null;
                }

                Resource resource = resourceOptional.get();

                try (Reader reader = resource.openAsReader()) {
                    return IOUtils.toString(reader);
                } catch (IOException exception) {
                    LOGGER.error("Coudln't preload {} shader {}: {}", new Object[]{type, id, exception});
                    return null;
                }
            };

            gpuDevice.precompilePipeline(pipeline, shaderSource);
        }
    }

     */
}
