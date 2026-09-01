package org.fish.Renderer;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.vertex.*;

public class Renderer {
    public static void Render() {
        ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(500);
        BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        bufferBuilder.addVertex(0, 0, 0).setColor(255, 255, 255, 255);
        bufferBuilder.addVertex(10, 0, 0).setColor(255, 255, 255, 255);
        bufferBuilder.addVertex(10, 10, 0).setColor(255, 255, 255, 255);

        MeshData meshData = bufferBuilder.build();



    }

}
