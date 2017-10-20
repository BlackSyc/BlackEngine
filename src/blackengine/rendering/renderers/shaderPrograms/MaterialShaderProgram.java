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
package blackengine.rendering.renderers.shaderPrograms;

import blackengine.gameLogic.Transform;
import blackengine.openGL.vao.Vao;
import blackengine.rendering.renderers.shaders.FragmentShader;
import blackengine.rendering.renderers.shaders.VertexShader;

/**
 * The base of all shader programs.
 *
 * @author Blackened
 * @param <M> The material that is used to load all uniforms to this programs
 * shaders.
 */
public abstract class MaterialShaderProgram<M extends Material> extends ShaderProgramBase {

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of ShaderProgram.
     *
     * @param vertexShader The vertex shader that will be used.
     * @param fragmentShader The fragment shader that will be used.
     */
    public MaterialShaderProgram(VertexShader vertexShader, FragmentShader fragmentShader) {
        super(vertexShader, fragmentShader);
    }
    //</editor-fold>

    /**
     * Loads all properties from the material to their corresponding uniform
     * variables.
     *
     * @param material The material of which the properties will be loaded into
     * the uniform variables.
     */
    public abstract void loadMaterialUniforms(M material);

    /**
     * Loads all uniform variables that are dependant on the transform of an
     * instance that will be renderer.
     *
     * @param transform The transform of the instance that will be rendered
     * next.
     */
    public abstract void loadTransformUniforms(Transform transform);

    /**
     * The draw call specific to the vao. The Vao should be bound and unbound in
     * this method as well.
     *
     * @param vao The vao that will be drawn.
     */
    public abstract void draw(Vao vao);

    /**
     * An implementation of this method should bind all attributes ('in'
     * variables) to a specified location using the member
     * {@link #bindAttribute(java.lang.String, int) bindAttribute(attributeName, location)}.
     */
    protected abstract void bindAttributes();

    @Override
    protected void validate() {
        this.bindAttributes();
        super.validate();
    }
}
