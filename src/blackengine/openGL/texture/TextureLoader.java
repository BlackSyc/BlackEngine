/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.texture;

import blackengine.dataAccess.dataObjects.ImageDataObject;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import blackengine.rendering.RenderEngine;

public class TextureLoader {

    /**
     * Creates a new Texture object referencing the image data on the graphics
     * card.
     *
     * @param data The image data that will be loaded onto the graphics card and
     * referenced by the result.
     * @return An instance of Texture referencing the image data on the graphics
     * card.
     */
    public static Texture createTexture(ImageDataObject data) {
        int texID = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE, data.getBuffer());

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0f);
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            if (RenderEngine.getInstance().isAnisotropicFilteringEnabled()) {
                float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
            }
        } else {
            System.out.println("no anisotropic filtering possible!");
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        return new Texture(texID, data.getWidth(), data.getHeight());
    }

}
