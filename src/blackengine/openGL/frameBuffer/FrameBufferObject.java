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
package blackengine.openGL.frameBuffer;

import blackengine.openGL.frameBuffer.exceptions.AttachmentAlreadyExistsException;
import blackengine.openGL.texture.FBOTexture;
import blackengine.openGL.texture.TextureLoader;
import blackengine.rendering.pipeline.Resolution;
import blackengine.toolbox.math.ImmutableVector3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Blackened
 */
public class FrameBufferObject {

    private final int frameBufferId;
    private final Resolution resolution;

    private FBOTexture colourTexture;
    private FBOTexture depthTexture;
    
    private ImmutableVector3 clearColour;

    public FBOTexture getColourTexture() {
        return colourTexture;
    }

    public FBOTexture getDepthTexture() {
        return depthTexture;
    }
    
    

    public FrameBufferObject(Resolution resolution, ImmutableVector3 clearColour) {
        this.frameBufferId = this.createFrameBuffer();
        this.resolution = resolution;
        this.clearColour = clearColour;
    }

    public boolean isValid() {
        return this.frameBufferId != -1
                && this.colourTexture != null
                && this.depthTexture != null;
    }
    
    public void startFrame(){
        this.bind();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(this.clearColour.getX(), this.clearColour.getY(), this.clearColour.getZ(), 1);
    }

    public void stopFrame() {
        this.unbind();
    }

    public void bind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBufferId);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL11.glViewport(0, 0, this.resolution.getWidth(), this.resolution.getHeight());
    }

    public void unbind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void destroy() {
        GL30.glDeleteFramebuffers(this.frameBufferId);

        if (this.colourTexture != null) {
            this.colourTexture.destroy();
        }
        if (this.depthTexture != null) {
            this.depthTexture.destroy();
        }
        this.colourTexture = null;
        this.depthTexture = null;

    }

    /**
     * Creates a new frame buffer object on the graphics card and returns its
     * ID.
     *
     * @return An integer representing the ID of the frame buffer object on the
     * graphics card.
     */
    private int createFrameBuffer() {
        int ID = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ID);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        return ID;
    }

    /**
     * Creates a texture to which the FBO can render its colour data.
     *
     * @param resolution
     */
    public void createTextureAttachment(Resolution resolution) {
        if (this.colourTexture != null) {
            throw new AttachmentAlreadyExistsException("colourTexture");
        }
        this.colourTexture = TextureLoader.createFrameBufferTexture(this.frameBufferId, resolution.getWidth(), resolution.getHeight());
    }

    /**
     * Creates a texture to which the FBO can render its depth data.
     *
     * @param resolution
     */
    public void createDepthTextureAttachment(Resolution resolution) {
        if (this.depthTexture != null) {
            throw new AttachmentAlreadyExistsException("depthTexture");
        }
        this.depthTexture = TextureLoader.createFrameBufferDepthTexture(this.frameBufferId, resolution.getWidth(), resolution.getHeight());
    }

}
