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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Blackened
 */
public class FrameBufferObject {

    private final int frameBufferId;
    private final int width;
    private final int height;

    private RenderBuffer depthBuffer;
    private FBOTexture colourTexture;
    private FBOTexture depthTexture;

    public RenderBuffer getDepthBuffer() {
        return depthBuffer;
    }

    public FBOTexture getColourTexture() {
        return colourTexture;
    }

    public FBOTexture getDepthTexture() {
        return depthTexture;
    }
    
    

    public FrameBufferObject(int width, int height) {
        this.frameBufferId = this.createFrameBuffer();
        this.width = width;
        this.height = height;
    }

    public boolean isValid() {
        return this.frameBufferId != -1
                && this.colourTexture != null
                && (this.depthBuffer != null || this.depthTexture != null);
    }

    public void bind() {
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBufferId);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL11.glViewport(0, 0, this.width, this.height);
    }

    public void unbind(int viewPortWidth, int viewPortHeight) {
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, viewPortWidth, viewPortHeight);
    }

    public void destroy() {
        GL30.glDeleteFramebuffers(this.frameBufferId);

        if (this.depthBuffer != null) {
            this.depthBuffer.destroy();
        }
        if (this.colourTexture != null) {
            this.colourTexture.destroy();
        }
        if (this.depthTexture != null) {
            this.depthTexture.destroy();
        }
        this.depthBuffer = null;
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
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    public void createTextureAttachment(int width, int height) {
        if (this.colourTexture != null) {
            throw new AttachmentAlreadyExistsException("colourTexture");
        }
        this.colourTexture = TextureLoader.createFrameBufferTexture(this.frameBufferId, width, height);
    }

    /**
     * Creates a texture to which the FBO can render its depth data.
     *
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    public void createDepthTextureAttachment(int width, int height) {
        if (this.depthTexture != null) {
            throw new AttachmentAlreadyExistsException("depthTexture");
        }
        this.depthTexture = TextureLoader.createFrameBufferDepthTexture(this.frameBufferId, width, height);
    }

    /**
     * Creates a render buffer object to which the depth information can be
     * rendered.
     *
     * @param width The width of the render buffer.
     * @param height The height of the render buffer.
     */
    public void createDepthAttachment(int width, int height) {
        if (this.depthBuffer != null) {
            throw new AttachmentAlreadyExistsException("depthBuffer");
        }
        int id = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, id);
        this.depthBuffer = new RenderBuffer(id);
    }

}
