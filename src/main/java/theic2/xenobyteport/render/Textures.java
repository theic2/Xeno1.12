package theic2.xenobyteport.render;

import static net.minecraft.client.renderer.texture.TextureUtil.glGenTextures;
import static net.minecraft.client.renderer.texture.TextureUtil.uploadTextureImage;

public class Textures {

    public static final int FONT = uploadTextureImage(glGenTextures(), Images.FONT);

}