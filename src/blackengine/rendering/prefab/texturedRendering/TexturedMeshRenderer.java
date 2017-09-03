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
package blackengine.rendering.prefab.texturedRendering;

import blackengine.gameLogic.components.prefab.rendering.TexturedMeshComponent;
import static blackengine.openGL.vao.vbo.AttributeType.TEXTURE_COORDS;
import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_POSITIONS;
import blackengine.rendering.renderers.TargetPOVRenderer;
import blackengine.toolbox.math.MatrixMath;
import java.util.HashSet;
import java.util.Set;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 */
public class TexturedMeshRenderer extends TargetPOVRenderer<TexturedMeshComponent> {

    private Set<TexturedMeshComponent> targets;

    public TexturedMeshRenderer() {
        super("/blackengine/rendering/prefab/texturedRendering/vertexShader.glsl", "/blackengine/rendering/prefab/texturedRendering/fragmentShader.glsl");
        this.targets = new HashSet<>();
    }

    @Override
    public void addRenderTarget(TexturedMeshComponent renderTarget) {
        this.targets.add(renderTarget);
    }

    @Override
    public void removeRenderTarget(TexturedMeshComponent renderTarget) {
        this.targets.remove(renderTarget);
    }

    @Override
    public boolean containsRenderTarget(TexturedMeshComponent renderTarget) {
        return this.targets.contains(renderTarget);
    }

    @Override
    public void render(Matrix4f viewMatrix) {
        this.initializeRendering(viewMatrix);
        this.targets.forEach(x -> {
            x.getVao().bind();
            x.getTexture().bindToUnit(GL13.GL_TEXTURE0);
            Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(x.getParent().getTransform().getAbsolutePosition(), x.getParent().getTransform().getEulerRotation(), x.getParent().getTransform().getScale());
            this.loadUniformMatrix("transformationMatrix", transformationMatrix);
            GL11.glDrawElements(GL11.GL_TRIANGLES, x.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            x.getVao().unbind();
        });
        this.finalizeRendering();
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getValue(), "position");
        super.bindAttribute(TEXTURE_COORDS.getValue(), "texCoords");
    }

    @Override
    public void initialize() {
        this.start();
        super.loadUniformMatrix("projectionMatrix", super.getProjectionMatrix());
        this.stop();
    }

    private void initializeRendering(Matrix4f viewMatrix) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.start();
        this.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    private void finalizeRendering() {
        this.stop();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

}
