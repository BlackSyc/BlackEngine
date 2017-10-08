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

import blackengine.rendering.renderers.RendererBase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.util.vector.Matrix4f;

/**
 * An instance of this class will deal with all rendering logic by coordinating
 * all other rendering classes.
 *
 * @author Blackened
 */
public class MasterRenderer {

    /**
     * All POV renderers belonging to this instance of MasterRenderer, mapped to
     * their class.
     */
    private Map<Class<? extends RendererBase>, RendererBase> renderers;

    /**
     * The projection matrix used in this MasterRenderer.
     */
    private Matrix4f projectionMatrix;

    /**
     * Retrieves a POV renderer of the specified class if it is present in this
     * master renderer.
     *
     * @param <T> The type of the POV renderer to be retrieved.
     * @param clazz The class of the POV renderer to be retrieved.
     * @return A POV renderer object of the specified class.
     */
    public <T extends RendererBase> T getRenderer(Class<T> clazz) {
        return clazz.cast(this.renderers.get(clazz));
    }

    /**
     * Default constructor for creating a new instance of MasterRenderer.
     */
    public MasterRenderer() {
        this.renderers = new HashMap<>();
    }

    /**
     * Adds a new renderer object to this master renderer.
     *
     * @param renderer The renderer to be added.
     */
    public void addRenderer(RendererBase renderer) {
        if (this.containsRenderer(renderer.getClass())) {
            this.getRenderer(renderer.getClass()).destroy();
        }
        this.renderers.put(renderer.getClass(), renderer);
    }

    /**
     * Verifies whether a POV renderer of the specified type is present in this
     * master renderer.
     *
     * @param clazz The class which will be used to check whether a POV renderer
     * is present.
     * @return True when a POV renderer of specified type is present, false
     * otherwise.
     */
    public boolean containsRenderer(Class<? extends RendererBase> clazz) {
        return this.renderers.containsKey(clazz);
    }
    
    /**
     * Destroys all renderers present in this master renderer.
     */
    public void destroy() {
        this.renderers.values().forEach(x -> x.destroy());
        this.renderers = new HashMap<>();
    }

    /**
     * Calls the render method on all of the registered POV renderers present in
     * this instance of MasterRenderer, in the order provided by the
     * RenderEngine.
     */
    public void render() {
        Iterator<Class<? extends RendererBase>> iter = RenderEngine.getInstance().getRendererIterator();

        while (iter.hasNext()) {
            Class<? extends RendererBase> rendererClass = iter.next();
            if (this.containsRenderer(rendererClass)) {
                this.getRenderer(rendererClass).render();
            }
        }
    }

}
