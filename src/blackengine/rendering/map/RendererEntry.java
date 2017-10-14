/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.map;

import blackengine.rendering.renderers.Material;
import blackengine.rendering.renderers.Renderer;
import blackengine.rendering.renderers.ShaderProgramBase;

/**
 * Represents a node in a renderer map. Each entry contains a renderer and the
 * class of the shader that the renderer uses.
 *
 * @author Blackened
 * @param <S> 
 * @param <M>
 */
public class RendererEntry<S extends ShaderProgramBase, M extends Material<S>> {

    /**
     * The class of the shader the renderer uses.
     */
    private final Class<S> shaderClass;

    /**
     * The renderer itself.
     */
    private final Renderer<S, M> renderer;

    /**
     * Getter for the class of the shader this renderer uses.
     *
     * @return The class of the shader the renderer uses.
     */
    public Class<S> getShaderClass() {
        return shaderClass;
    }

    /**
     * Getter for the renderer itself.
     *
     * @return The renderer itself.
     */
    public Renderer<S, M> getRenderer() {
        return renderer;
    }

    /**
     * Protected default constructor for creating a new instance of
     * RendererEntry.
     *
     * @param shaderClass The class of the shader the renderer uses.
     * @param renderer The renderer itself.
     */
    protected RendererEntry(Class<S> shaderClass, Renderer<S, M> renderer) {
        this.shaderClass = shaderClass;
        this.renderer = renderer;
    }

}
