package org.fish.Renderer.Text;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.gui.font.providers.FreeTypeUtil;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.fish.FishPlusPlusV2;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FT_GlyphSlot;
import org.lwjgl.util.freetype.FreeType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.fish.FishPlusPlusV2.mc;

public class FontLoader {

    public static HashMap<String, Font> fonts = new HashMap<>();
    public static Font activeFont = null;

    public static void LoadFont(String fontPath, String fontName) {
        if (fonts.containsKey(fontName))
            return;
        FT_Face face;
        ByteBuffer fontData;

        try {
            InputStream resource = mc.getResourceManager().open(FishPlusPlusV2.id(fontPath));
            fontData = TextureUtil.readResource(resource);

            MemoryStack stack = MemoryStack.stackPush();

            PointerBuffer faceBuffer = stack.mallocPointer(1);

            FreeType.FT_New_Memory_Face(FreeTypeUtil.getLibrary(), fontData, 0, faceBuffer);
            face = FT_Face.create(faceBuffer.get());
            FreeType.FT_Set_Pixel_Sizes(face, 0 , 24);
            stack.pop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        FT_GlyphSlot g;
        int h = 0;
        int w = 0;

        HashMap<java.lang.Character, Character> characters = new HashMap<>();

        for (char c = 32; c < 128; c++) {
            if (FreeType.FT_Load_Char(face, c, FreeType.FT_LOAD_RENDER) == 1)
                continue;

            g = face.glyph();

            w += g.bitmap().width();
            h = Mth.absMax(h, g.bitmap().rows());
        }

        NativeImage nativeImage = new NativeImage(NativeImage.Format.RGBA, w, h, false);

        int x = 0;

        for (char c = 32; c < 128; c++) {
            if (FreeType.FT_Load_Char(face, c, FreeType.FT_LOAD_RENDER) == 1)
                continue;

            g = face.glyph();

            Character character = new Character(
                    new Vec2(face.glyph().bitmap().width(), face.glyph().bitmap().rows()),
                    new Vec2(face.glyph().bitmap_left(), face.glyph().bitmap_top()),
                    (int) (g.advance().x() >> 6),
                    (int) (face.size().metrics().ascender() >> 6),
                    (float) x / w,
                    (float) g.bitmap().width() / w
                    );
            characters.put(c, character);

            int size = Math.abs(g.bitmap().pitch()) * g.bitmap().rows();
            ByteBuffer buffer = g.bitmap().buffer(size);

            for (int pixelY = 0; pixelY < g.bitmap().rows(); pixelY++) {
                for (int pixelX = 0; pixelX < g.bitmap().width(); pixelX++) {
                    int alpha = buffer.get(pixelY * g.bitmap().pitch() + pixelX) & 0xFF;

                    nativeImage.setPixelABGR(x + pixelX, pixelY, (alpha << 24) | 0x00FFFFFF);
                }
            }
            x += g.bitmap().width();
        }

        FreeType.FT_Done_Face(face);
        DynamicTexture dynamicTexture = new DynamicTexture(() -> "FishPlusPlus Font Texture", nativeImage);
        Font font = new Font(characters, dynamicTexture, 12, w, h);

        if (activeFont == null)
            activeFont = font;
        fonts.put(fontName, font);
    }
}
