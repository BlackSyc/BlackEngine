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
import blackengine.gameLogic.Transform;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.vbo.AttributeType;
import static blackengine.openGL.vao.vbo.AttributeType.TEXTURE_COORDS;
import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_POSITIONS;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.renderers.ShaderProgram;
import blackengine.rendering.renderers.shaders.FragmentShader;
import blackengine.rendering.renderers.shaders.VertexShader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 *
 * @author Blackened
 */
public class SimpleShaderProgram extends ShaderProgram<SimpleMaterial>{
    
    private static final String VERTEX_PATH = "/blackengine/rendering/prefab/simple/vertexShader.glsl";
    
    private static final String FRAGMENT_PATH = "/blackengine/rendering/prefab/simple/fragmentShader.glsl";

    public SimpleShaderProgram() throws IOException {
        super(new VertexShader("simpleVertex", PlainTextLoader.loadResource(VERTEX_PATH)), 
                new FragmentShader("simpleFragment", PlainTextLoader.loadResource(FRAGMENT_PATH)));
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute("position", VERTEX_POSITIONS.getValue());
        super.bindAttribute("textureCoords", TEXTURE_COORDS.getValue());
    }

    @Override
    public void loadGlobalUniforms() {
        super.loadUniformMatrix("projectionMatrix", RenderEngine.getInstance().getProjectionMatrix());
    }

    @Override
    public void loadFrameUniforms() {
        super.loadUniformMatrix("viewMatrix", RenderEngine.getInstance().getMainCamera().getViewMatrix());
    }

    @Override
    public void loadMaterialUniforms(SimpleMaterial material) {
        material.getTexture().bindToUnit(GL13.GL_TEXTURE0);
    }

    @Override
    public void loadTransformUniforms(Transform transform) {
        super.loadUniformMatrix("transformationMatrix", transform.createTransformationMatrix());
    }

    @Override
    public void draw(Vao vao) {
        vao.bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    @Override
    public void applySettings() {
        
    }
    
}
