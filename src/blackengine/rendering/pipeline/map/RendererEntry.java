/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.pipeline.map;

import blackengine.rendering.pipeline.shaderPrograms.Material;
import blackengine.rendering.pipeline.elements.Renderer;
import blackengine.rendering.pipeline.shaderPrograms.MaterialShaderProgram;

/**
 * Represents a node in a renderer map. Each entry contains a renderer and the
 * class of the shader that the renderer uses.
 *
 * @author Blackened
 * @param <S> 
 * @param <M>
 */
public class RendererEntry<S extends MaterialShaderProgram, M extends Material<S>> {

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
    public RendererEntry(Class<S> shaderClass, Renderer<S, M> renderer) {
        this.shaderClass = shaderClass;
        this.renderer = renderer;
    }

}
