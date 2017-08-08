/* 
 * The MIT License
 *
 * Copyright 2017 Blackened.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
