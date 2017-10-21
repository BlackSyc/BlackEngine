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
package blackengine.rendering.pipeline.shaderPrograms;

import blackengine.rendering.pipeline.shaderPrograms.MaterialShaderProgram;

/**
 * An instance of this class should expose all material uniforms used in the
 * specified shader class.
 *
 * @author Blackened
 * @param <S>
 */
public abstract class Material<S extends MaterialShaderProgram> {

    /**
     * The class of shader program this material exposes the uniform variables for.
     */
    private final Class<S> shaderClass;

    /**
     * Getter for the class of shader program this material exposes the uniform
     * variables for.
     *
     * @return A class that extends ShaderProgram.
     */
    public Class<S> getShaderClass() {
        return this.shaderClass;
    }

    /**
     * Default constructor for creating a new instance of Material.
     *
     * @param shaderClass The class of shader program that this material will
     * expose the uniform variables to.
     */
    public Material(Class<S> shaderClass) {
        this.shaderClass = shaderClass;
    }

}
