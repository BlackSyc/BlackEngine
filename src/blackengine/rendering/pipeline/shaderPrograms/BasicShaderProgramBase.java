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

import blackengine.gameLogic.Transform;
import blackengine.openGL.vao.Vao;
import static blackengine.openGL.vao.vbo.AttributeType.TEXTURE_COORDS;
import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_POSITIONS;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.pipeline.shaderPrograms.shaders.FragmentShader;
import blackengine.rendering.pipeline.shaderPrograms.shaders.VertexShader;
import org.lwjgl.opengl.GL11;

/**
 * This base class extends
 * {@link blackengine.rendering.pipeline.shaderPrograms.MaterialShaderProgram} with some most used
 * implementations of abstract methods such that the projection matrix, view
 * matrix, transform matrix will be loaded into their uniform variables.
 *
 * @author Blackened
 * @param <M>
 */
public abstract class BasicShaderProgramBase<M extends Material> extends MaterialShaderProgram<M> {

    /**
     * Default constructor for creating a new instance of basic shader program
     * base.
     *
     * @param vertexShader The vertex shader that will be used.
     * @param fragmentShader The fragment shader that will be used.
     */
    public BasicShaderProgramBase(VertexShader vertexShader, FragmentShader fragmentShader) {
        super(vertexShader, fragmentShader);
    }

    /**
     * Binds the attributes to their correct position in the vao.
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute("position", VERTEX_POSITIONS.getValue());
        super.bindAttribute("textureCoords", TEXTURE_COORDS.getValue());
    }

    /**
     * Loads the view matrix uniform variable.
     */
    @Override
    public void loadFrameUniforms() {
        super.loadUniformMatrix("projectionMatrix", this.getCamera().getProjectionMatrix());
        super.loadUniformMatrix("viewMatrix", this.getCamera().getViewMatrix());
    }

    /**
     * Loads the transformation matrix uniform variable.
     *
     * @param transform The transform from which a transformation matrix will be
     * loaded to the uniform variable.
     */
    @Override
    public void loadTransformUniforms(Transform transform) {
        super.loadUniformMatrix("transformationMatrix", transform.createTransformationMatrix());
    }

    /**
     * Draws the vao to the screen.
     *
     * @param vao The vao that will be drawn to the screen.
     */
    @Override
    public void draw(Vao vao) {
        vao.bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    /**
     * Enables blending and depth testing.
     */
    @Override
    public void applySettings() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
}
