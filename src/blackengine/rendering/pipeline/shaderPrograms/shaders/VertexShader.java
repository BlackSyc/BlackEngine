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
package blackengine.rendering.pipeline.shaderPrograms.shaders;

import blackengine.rendering.pipeline.shaderPrograms.shaders.exceptions.ShaderCompileException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Blackened
 */
public final class VertexShader extends Shader {

    /**
     * Default constructor for creating a new instance of VertexShader.
     * @param name The name of the shader (primarily used for debugging purposes).
     * @param shaderSource The source of the shader written in GLSL.
     */
    public VertexShader(String name, String shaderSource) {
        super(name, shaderSource);
    }

    /**
     * Creates and compiles the vertex shader from the source.
     * @param shaderSource The source of the shader written in GLSL.
     * @return An integer referencing the shader on the graphics card.
     */
    @Override
    protected int create(String shaderSource) {
        int shaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new ShaderCompileException("Vertex", this.getName());
        }
        return shaderId;
    }

}
