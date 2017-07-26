/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;

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

        RenderEngine.getInstance().getMasterRenderer().render();

        this.updateDisplay();
    }

    public void updateDisplay() {

        Display.sync(this.fpsCap);

        if (Display.wasResized()) {
            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        }

        Display.update();

    }
    
    public void createEngine(){
        RenderEngine.create();
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

        RenderEngine.getInstance().getMasterRenderer().createProjectionMatrix(width, height, 70f, 0.1f, 500f);
    }

    public void destroyDisplay() {
        Display.destroy();
    }

}
