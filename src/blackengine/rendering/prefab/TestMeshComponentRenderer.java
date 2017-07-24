/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.prefab;

import blackengine.gameLogic.components.prefab.TestMeshRenderComponent;
import blackengine.openGL.texture.Texture;
import static blackengine.openGL.vao.vbo.AttributeType.*;
import blackengine.rendering.ComponentRenderer;
import blackengine.toolbox.math.MatrixMath;
import java.util.HashMap;
import java.util.HashSet;
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
public class TestMeshComponentRenderer extends ComponentRenderer<TestMeshRenderComponent> {

    private HashMap<Texture, Set<TestMeshRenderComponent>> renderTargets;

    public TestMeshComponentRenderer() {
        super("/blackengine/rendering/prefab/vertexShader.glsl", "/blackengine/rendering/prefab/fragmentShader.glsl");
        this.renderTargets = new HashMap<>();
    }

    @Override
    public void render(Matrix4f viewMatrix) {
        this.initializeRendering(viewMatrix);
        this.renderTargets.forEach((x, y) -> {
            x.bindToUnit(GL13.GL_TEXTURE0);
            y.forEach(z -> {
                z.getVao().bind();
                Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(z.getParent().getAbsolutePosition(), z.getParent().getRotation(), 1);
                this.loadUniformMatrix("transformationMatrix", transformationMatrix);
                GL11.glDrawElements(GL11.GL_TRIANGLES, z.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                z.getVao().unbind();
            });
            x.unbind();
        });
        this.finalizeRendering();
    }

    @Override
    public void addRenderTarget(TestMeshRenderComponent renderComponent) {
        if (!this.renderTargets.containsKey(renderComponent.getTexture())) {
            this.renderTargets.put(renderComponent.getTexture(), new HashSet<>());
        }
        this.renderTargets.get(renderComponent.getTexture()).add(renderComponent);
    }

    @Override
    public void removeRenderTarget(TestMeshRenderComponent renderComponent) {
        if (this.renderTargets.containsKey(renderComponent.getTexture())) {
            this.renderTargets.get(renderComponent.getTexture()).remove(renderComponent);
        }
    }

    @Override
    public boolean containsRenderTarget(TestMeshRenderComponent renderComponent) {
        return this.renderTargets.values().stream().anyMatch(x -> x.contains(renderComponent));
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getValue(), "position");
        super.bindAttribute(TEXTURE_COORDS.getValue(), "textureCoords");
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
        this.renderTargets.forEach((x, y) -> {
            y.forEach(z -> {
                z.setRenderer(null);
            });
        });
        this.renderTargets = new HashMap<>();
        super.destroy();
        System.out.println(this.getClass().getSimpleName() + " destroyed!");
    }

    private void initializeRendering(Matrix4f viewMatrix) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        //GL11.glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        this.start();
        this.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    private void finalizeRendering() {
        this.stop();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        //GL11.glPolygonMode( GL_FRONT_AND_BACK, GL_FILL);
    }

}
