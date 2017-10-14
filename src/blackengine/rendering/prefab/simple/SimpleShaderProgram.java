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
package blackengine.rendering.prefab.simple;

import blackengine.dataAccess.tools.PlainTextLoader;
import blackengine.rendering.renderers.BasicShaderProgramBase;
import blackengine.rendering.renderers.shaders.FragmentShader;
import blackengine.rendering.renderers.shaders.VertexShader;
import java.io.IOException;
import org.lwjgl.opengl.GL13;

/**
 * Very simple shader program for rendering shapes with textures.
 *
 * @author Blackened
 */
public class SimpleShaderProgram extends BasicShaderProgramBase<SimpleMaterial> {

    /**
     * The path to the vertex shader source.
     */
    private static final String VERTEX_PATH = "/blackengine/rendering/prefab/simple/vertexShader.glsl";

    /**
     * The path to the fragment shader source.
     */
    private static final String FRAGMENT_PATH = "/blackengine/rendering/prefab/simple/fragmentShader.glsl";

    /**
     * Default constructor for creating a new instance of SimpleShaderProgram.
     *
     * @throws IOException Throws an IOException when the shader sources were
     * not found.
     */
    public SimpleShaderProgram() throws IOException {
        super(new VertexShader("simpleVertex", PlainTextLoader.loadResource(VERTEX_PATH)),
                new FragmentShader("simpleFragment", PlainTextLoader.loadResource(FRAGMENT_PATH)));
    }

    /**
     * Loads the material uniforms to the shader program.
     *
     * @param material The material for the instance that is to be rendered
     * next.
     */
    @Override
    public void loadMaterialUniforms(SimpleMaterial material) {
        material.getTexture().bindToUnit(GL13.GL_TEXTURE0);
    }
}
