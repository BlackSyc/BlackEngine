/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.prefab;

import blackengine.gameLogic.components.prefab.TestMeshComponent;
import blackengine.openGL.texture.Texture;
import static blackengine.openGL.vao.vbo.AttributeType.*;
import blackengine.rendering.ComponentRenderer;
import blackengine.toolbox.math.MatrixMath;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 */
public class TestMeshComponentRenderer extends ComponentRenderer<TestMeshComponent> {

    private Set<TestMeshComponent> renderTargets;

    public TestMeshComponentRenderer() {
        super("/blackengine/rendering/prefab/vertexShader.glsl", "/blackengine/rendering/prefab/fragmentShader.glsl");
        this.renderTargets = new HashSet<>();
    }

    @Override
    public void render(Matrix4f viewMatrix) {
        this.initializeRendering(viewMatrix);

        this.renderTargets.forEach(x -> {
            x.getVao().bind();
            Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(x.getParent().getAbsolutePosition(), x.getParent().getRotation(), 1);
            this.loadUniformMatrix("transformationMatrix", transformationMatrix);
            GL11.glDrawElements(GL11.GL_TRIANGLES, x.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            x.getVao().unbind();
        });
        this.finalizeRendering();
    }

    @Override
    public void addRenderTarget(TestMeshComponent renderComponent) {
        this.renderTargets.add(renderComponent);
    }

    @Override
    public void removeRenderTarget(TestMeshComponent renderComponent) {
        this.renderTargets.remove(renderComponent);
    }

    @Override
    public boolean containsRenderTarget(TestMeshComponent renderComponent) {
        return this.renderTargets.contains(renderComponent);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getValue(), "position");
    }

    /**
     *
     */
    @Override
    public void initialize() {
        this.start();
        super.loadUniformMatrix("projectionMatrix", super.getProjectionMatrix());
        this.stop();
    }

    @Override
    public void destroy() {
        this.renderTargets.forEach(x -> {
            x.setRenderer(null);
        });
        this.renderTargets = new HashSet<>();
        super.destroy();
    }

    private void initializeRendering(Matrix4f viewMatrix) {
        GL11.glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        this.start();
        this.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    private void finalizeRendering() {
        this.stop();
        GL11.glPolygonMode( GL_FRONT_AND_BACK, GL_FILL);
    }

}
