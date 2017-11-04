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
package blackengine.rendering.pipeline.framebuilding.prefab;

import blackengine.dataAccess.tools.PlainTextLoader;
import blackengine.gameLogic.Transform;
import blackengine.gameLogic.components.prefab.CameraComponent;
import blackengine.openGL.prefab.Quad;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.VaoLoader;
import static blackengine.openGL.vao.vbo.AttributeType.TEXTURE_COORDS;
import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_POSITIONS;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.pipeline.shaderPrograms.ProcessingShaderProgram;
import blackengine.rendering.pipeline.shaderPrograms.shaders.FragmentShader;
import blackengine.rendering.pipeline.shaderPrograms.shaders.VertexShader;
import blackengine.toolbox.math.ImmutableVector3;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 *
 * @author Blackened
 */
public class DefaultFrameBuildingShaderProgram extends ProcessingShaderProgram {

    /**
     * The path to the vertex shader source.
     */
    private static final String VERTEX_PATH = "/blackengine/rendering/pipeline/framebuilding/prefab/VertexShader.glsl";

    /**
     * The path to the fragment shader source.
     */
    private static final String FRAGMENT_PATH = "/blackengine/rendering/pipeline/framebuilding/prefab/FragmentShader.glsl";

    private Vao quadVao;

    private final String mainCameraIdentifier;

    public DefaultFrameBuildingShaderProgram(String mainCameraIdentifier) throws IOException {
        super(new VertexShader("DefaultFrameBuildingVertexShader", PlainTextLoader.loadResource(VERTEX_PATH)),
                new FragmentShader("DefaultFrameBuildingFragmentShader", PlainTextLoader.loadResource(FRAGMENT_PATH)));
        this.mainCameraIdentifier = mainCameraIdentifier;
        Quad quad = new Quad();
        this.quadVao = VaoLoader.loadVao(quad);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute("position", VERTEX_POSITIONS.getValue());
        super.bindAttribute("textureCoords", TEXTURE_COORDS.getValue());
    }

    @Override
    public void process() {

        CameraComponent mainCamera = RenderEngine.getInstance().getCamera(mainCameraIdentifier);

        if (mainCamera != null) {

            mainCamera.getFrameBuffer().getColourTexture().bindToUnit(GL13.GL_TEXTURE0);

            this.quadVao.bind();

            GL11.glDrawElements(GL11.GL_TRIANGLES, this.quadVao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

            this.quadVao.unbind();

            mainCamera.getFrameBuffer().getColourTexture().unbind();
        }else{
            this.quadVao.bind();

            GL11.glDrawElements(GL11.GL_TRIANGLES, this.quadVao.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

            this.quadVao.unbind();
        }
    }

    @Override
    public void onInitialize() {
        //Transform transform = new Transform(new ImmutableVector3(-1f, -1f, 0), new ImmutableVector3(), new ImmutableVector3(2f, 2f, 1));
        //super.loadUniformMatrix("transformationMatrix", transform.createTransformationMatrix());
        super.loadUniformMatrix("transformationMatrix", new Transform().createTransformationMatrix());
    }

    @Override
    public void loadFrameUniforms() {
    }

    @Override
    public void applySettings() {
    }

}
