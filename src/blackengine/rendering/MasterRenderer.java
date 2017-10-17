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
import blackengine.rendering.map.RendererMap;
import blackengine.rendering.renderers.Material;
import blackengine.rendering.renderers.Renderer;
import blackengine.rendering.renderers.ShaderProgramBase;

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
    private final RendererMap rendererMap;

    /**
     * Retrieves the renderer that can be used to render the specified render
     * component if one is present.
     *
     * @param <S>
     * @param <M>
     * @param renderComponent The render component for which a compatible
     * renderer will be retrieved, if one is present.
     * @return An instance of Renderer that is compatible with the specified
     * render component, or null if none was found.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> Renderer<S, M> getRendererFor(RenderComponent<S, M> renderComponent) {
        return this.rendererMap.getRendererFor(renderComponent);
    }

    /**
     * Checks whether a renderer is present in this master renderers renderer
     * map.
     *
     * @param <S>
     * @param <M>
     * @param renderComponent The render component for which the presence of a
     * compatible renderer will be checked.
     * @return True if a renderer is present that is compatible with the
     * specified component, false otherwise.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> boolean containsRendererFor(RenderComponent<S, M> renderComponent) {
        return this.rendererMap.containsRendererFor(renderComponent);
    }

    /**
     * Retrieves a renderer that has a shader program of the specified shader
     * program class.
     *
     * @param <S>
     * @param <M>
     * @param shaderClass The class of the shader program that will be in the
     * retrieved renderer.
     * @return A renderer with a shader program of the specified class, if one
     * exists. Returns null otherwise.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> Renderer<S, M> getRendererWith(Class<S> shaderClass) {
        return this.rendererMap.get(shaderClass);
    }

    /**
     * Puts a renderer in this master renderers renderer map. If one with the
     * same shader class was already present, it will be replaced.
     *
     * @param <S>
     * @param <M>
     * @param renderer An instance of Renderer that will be put in the renderer
     * map.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> void put(Renderer<S, M> renderer) {
        this.rendererMap.put(renderer);
        renderer.initialize();
    }

    /**
     * Default constructor for creating a new instance of MasterRenderer.
     */
    public MasterRenderer() {
        this.rendererMap = new RendererMap();
    }

    /**
     * Calls the render method on each of the renderers that are present in the
     * renderer map. Also removes all renderers that are flagged as destroyed.
     */
    public void render() {
        this.removeDestroyedRenderers();
        this.rendererMap.getRenderers()
                .forEach(x -> x.render());
    }

    /**
     * Removes all renderers from the renderer map that are flagged as
     * destroyed.
     */
    public void removeDestroyedRenderers() {
        this.rendererMap.getRenderers()
                .filter(x -> x.isDestroyed())
                .forEach(x -> this.rendererMap.removeRenderer(x));
    }

    /**
     * Destroys each of the renderers present in the renderer map.
     */
    public void destroy() {
        this.rendererMap.destroy();
    }

}
