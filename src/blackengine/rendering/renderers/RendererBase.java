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
package blackengine.rendering.renderers;

import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import java.util.HashSet;

/**
 *
 * @author Blackened
 * @param <T>
 */
public abstract class RendererBase<T extends Material<? extends ShaderProgram<T>>> {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    protected ShaderProgram<T> shaderProgram;

    private HashSet<RenderComponent<T>> targets;
    
    public Class<? extends ShaderProgram> getShaderClass(){
        return this.shaderProgram.getClass();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of RenderBase.
     *
     */
    public RendererBase(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public abstract void render();

    public abstract void destroy();

    public abstract void initialize();

    /**
     * Adds the render target to this renderers set of targets. This method can
     * be overridden if special grouping or sorting is required.
     *
     * @param renderTarget The render target that will be added to this
     * renderers target set.
     */
    public void addRenderTarget(RenderComponent<T> renderTarget) {
        this.targets.add(renderTarget);
    }

    /**
     * Removes the render target from this renderers set of targets if it
     * exists. This method can be overridden if a different implementation of
     * the target set was made.
     *
     * @param renderTarget The render target that will be removed from this
     * renderers target set, if possible.
     */
    public void removeRenderTarget(RenderComponent<T> renderTarget) {
        this.targets.remove(renderTarget);
    }

    /**
     * Checks whether the render target is present in this renderers target set.
     * This method can be overridden if a different implementation of the target
     * set was made.
     *
     * @param renderTarget The render target that will be checked for in this
     * renderers set of targets.
     * @return True if the render target is present, false otherwise.
     */
    public boolean containsRenderTarget(RenderComponent<T> renderTarget) {
        return this.targets.contains(renderTarget);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Protected Methods">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    //</editor-fold>
}
