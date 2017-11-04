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
package blackengine.rendering;

import blackengine.toolbox.math.ImmutableVector3;
import java.awt.Canvas;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author Blackened
 */
public class DisplayManager {

    private final int fpsCap;    
    
    public DisplayManager(int fpsCap) {
        this.fpsCap = fpsCap;
    }

    public void render() {
        RenderEngine.getInstance().render();

        this.updateDisplay();
    }

    public void updateDisplay() {

        Display.sync(this.fpsCap);

        if (Display.wasResized()) {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        }

        Display.update();

    }

    public void createEngine() {
        RenderEngine.create();
    }

    public void destroyEngine() {
        if (RenderEngine.getInstance() != null) {
            RenderEngine.getInstance().destroy();
        }
    }

    public void createDisplay(int width, int height, String title, boolean fullScreen) {
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            if (!fullScreen) {
                Display.setDisplayMode(new DisplayMode(width, height));
            }
            Display.create(new PixelFormat().withSamples(8).withDepthBits(24), attribs);

            Display.setResizable(true);
            Display.setTitle(title);
            GL11.glEnable(GL13.GL_MULTISAMPLE);
            Display.setVSyncEnabled(true);
            Display.setFullscreen(fullScreen);

        } catch (LWJGLException ex) {
            Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        GL11.glViewport(0, 0, width, height);

    }

    public void embed(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setParent(canvas);
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat().withSamples(8).withDepthBits(24), attribs);

            Display.setResizable(true);
            GL11.glEnable(GL13.GL_MULTISAMPLE);
            Display.setVSyncEnabled(true);

        } catch (LWJGLException ex) {
            Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        GL11.glViewport(0, 0, width, height);
    }
    
    public void destroyDisplay() {
        Display.destroy();
    }

}
