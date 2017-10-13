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
import blackengine.rendering.renderers.ShaderProgram;

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
    private RendererMap rendererMap;

    public <S extends ShaderProgram, M extends Material<S>> Renderer<S, M> getRendererFor(RenderComponent<S,M> renderComponent) {
        return this.rendererMap.getRendererFor(renderComponent);
    }

    public <S extends ShaderProgram, M extends Material<S>> boolean containsRendererFor(RenderComponent<S,M> renderComponent) {
        return this.rendererMap.containsRendererFor(renderComponent);
    }
    
    public <S extends ShaderProgram, M extends Material<S>> Renderer<S, M> getRendererWith(Class<S> shaderClass) {
        return this.rendererMap.get(shaderClass);
    }
    
    public <S extends ShaderProgram, M extends Material<S>> void put(Renderer<S, M> renderer){
        this.rendererMap.put(renderer);
    }

    public void render() {
        this.rendererMap.getRenderers()
                .forEach(x -> x.render());
    }

    public void destroy() {
        this.rendererMap.destroy();
    }

}
