/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

    private final int textureId;
    private final int type = GL11.GL_TEXTURE_2D;
    private int unit;
    private int atlasSize = 1;
    private int atlasIndex = 0;
    private int imageWidth;
    private int imageHeight;

    public void setAtlasSize(int atlasSize) {
        this.atlasSize = atlasSize;
    }

    public void setAtlasIndex(int atlasIndex) {
        this.atlasIndex = atlasIndex;
    }

    public int getAtlasSize() {
        return atlasSize;
    }

    public int getAtlasIndex() {
        return atlasIndex;
    }
    
    public int getTextureId(){
        return this.textureId;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
    
    public Texture(int textureId, int imageWidth, int imageHeight) {
        this.textureId = textureId;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public void bindToUnit(int unit) {
        this.unit = unit;
        GL13.glActiveTexture(this.unit);
        GL11.glBindTexture(type, textureId);
    }

    public void unbind() {
        GL13.glActiveTexture(this.unit);
        GL11.glBindTexture(type, 0);
    }

    public void delete() {
        GL11.glDeleteTextures(textureId);
    }

}
