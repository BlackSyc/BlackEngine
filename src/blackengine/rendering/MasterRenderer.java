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

import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import blackengine.rendering.renderers.Material;
import blackengine.rendering.renderers.RendererBase;
import blackengine.rendering.renderers.ShaderProgram;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * An instance of this class will deal with all rendering logic by coordinating
 * all other rendering classes.
 *
 * @author Blackened
 */
public class MasterRenderer {

    /**
     * All renderers that have been added to this instance of Master Renderer.
     */
    private Map<Class<? extends ShaderProgram<? extends Material<?>>>, RendererBase> renderers;

    /**
     * Retrieves a renderer with a shader program of the specified class, if one
     * exists.
     *
     * @param <S> The type of the shader program.
     * @param <M>
     * @param clazz The class of the shader program for which the renderer will
     * be found.
     * @return A renderer that has the shader program of the specified type, or
     * null if none was found.
     */
    public <M extends Material<S>, S extends ShaderProgram<M>> RendererBase<M> getRendererFor(Class<S> clazz) {
        return this.renderers.get(clazz);
    }

    /**
     * Retrieves a renderer that can render the specified component if one
     * exists.
     *
     * @param <Q>
     * @param renderComponent The component for which the compatible renderer
     * will be retrieved.
     * @return A renderer that can render the specified component, or null if
     * none was found.
     */
    public <Q extends Material<? extends ShaderProgram>> RendererBase<Q> getRendererFor(RenderComponent<Q> renderComponent) {
        return this.renderers.get(renderComponent.getMaterial().getShaderClass());
    }

    /**
     * Default constructor for creating a new instance of MasterRenderer.
     */
    public MasterRenderer() {
        this.renderers = new HashMap<>();
    }

    /**
     * Adds a new renderer object to this master renderer. If a renderer for the
     * same shader program class was already found, it will be destroyed and
     * replaced with the presented renderer.
     *
     * @param renderer The renderer to be added.
     */
    public void addRenderer(RendererBase renderer) {
        if (this.containsRendererFor(renderer.getShaderClass())) {
            this.getRendererFor(renderer.getShaderClass()).destroy();
        }
        this.renderers.put(renderer.getShaderClass(), renderer);
        renderer.initialize();
    }

    /**
     * Checks whether there exists a renderer for the specified shader program
     * class.
     *
     * @param clazz The class of the shader program for which the master
     * renderer will check presence of a renderer in its renderer list.
     * @return True if a renderer for the same shader program class is present
     * in the renderer list, false otherwise.
     */
    public boolean containsRendererFor(Class<? extends ShaderProgram> clazz) {
        return this.renderers.containsKey(clazz);
    }

    /**
     * Checks whether there exists a renderer in this master renderer instance
     * that can render the specified component.
     *
     * @param renderComponent The render component which will be used to see if
     * there is a compatible renderer present in this instance of master
     * renderer.
     * @return True if a renderer is present in the renderer list that is
     * compatible with the specified render component, false otherwise.
     */
    public boolean containsRendererFor(RenderComponent<?> renderComponent) {
        return this.renderers.containsKey(renderComponent.getMaterial().getShaderClass());
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
//        Iterator<Class<? extends RendererBase>> iter = RenderEngine.getInstance().getRendererIterator();
//
//        while (iter.hasNext()) {
//            Class<? extends RendererBase> rendererClass = iter.next();
//            if (this.containsRenderer(rendererClass)) {
//                this.getRenderer(rendererClass).render();
//            }
//        }
    }

}
