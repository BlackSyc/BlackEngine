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
package blackengine.rendering.prefab.testing;

import blackengine.dataAccess.dataObjects.ImageDataObject;
import blackengine.dataAccess.dataObjects.MeshDataObject;
import blackengine.dataAccess.fileLoaders.ImageLoader;
import blackengine.dataAccess.fileLoaders.MeshLoader;
import blackengine.dataAccess.tools.PlainTextLoader;
import blackengine.gameLogic.GameElement;
import blackengine.gameLogic.GameManager;
import blackengine.gameLogic.components.prefab.collision.BoxCollisionComponent;
import blackengine.gameLogic.components.prefab.collision.SphereCollisionComponent;
import blackengine.gameLogic.components.prefab.collision.base.CollisionComponent;
import blackengine.gameLogic.components.prefab.rendering.DebugRenderComponent;
import blackengine.openGL.texture.Texture;
import blackengine.openGL.texture.TextureLoader;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.VaoLoader;
import static blackengine.openGL.vao.vbo.AttributeType.*;
import blackengine.rendering.Camera;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.renderers.RendererBase;
import blackengine.rendering.renderers.ShaderProgram;
import blackengine.rendering.renderers.shaders.FragmentShader;
import blackengine.rendering.renderers.shaders.VertexShader;
import blackengine.toolbox.math.ImmutableVector3;
import java.io.IOException;
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
public class DebugRenderer extends RendererBase<DebugRenderComponent> {

    private Set<DebugRenderComponent> renderTargets;

    private Vao unitSphere;

    private Vao unitCube;

    private int gridVao;

    private boolean gridEnabled = false;

    private boolean renderCollidersEnabled = false;

    private GameManager gameManager;

    public void setGridVao(int gridVao) {
        this.gridVao = gridVao;
    }

    public void setUnitSphere(Vao unitSphere) {
        this.unitSphere = unitSphere;
    }

    public void setUnitCube(Vao unitCube) {
        this.unitCube = unitCube;
    }

    public boolean isGridEnabled() {
        return gridEnabled;
    }

    public void setGridEnabled(boolean gridEnabled) {
        this.gridEnabled = gridEnabled;
    }

    public boolean isRenderCollidersEnabled() {
        return renderCollidersEnabled;
    }

    public void setRenderCollidersEnabled(boolean renderCollidersEnabled) {
        this.renderCollidersEnabled = renderCollidersEnabled;
    }

    protected DebugRenderer(GameManager gameManager, ShaderProgram shaderProgram) {
        super(shaderProgram);
        this.gameManager = gameManager;
        this.renderTargets = new HashSet<>();
    }

    @Override
    public void render() {
        Matrix4f viewMatrix = RenderEngine.getInstance().getMainCamera().getViewMatrix();
        this.initializeRendering(viewMatrix);

        if (this.renderCollidersEnabled) {
            this.renderSphereColliders();
            this.renderBoxColliders();
        }

        this.renderTargets.forEach(x -> {
            x.getVao().bind();

            boolean unbindTexture = false;
            if (!x.isWireFrameEnabled()) {
                GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                x.getTexture().bindToUnit(GL13.GL_TEXTURE0);
                this.shaderProgram.loadUniformBool("textured", true);
                this.shaderProgram.loadUniformVector3f("colour", new ImmutableVector3(1, 1, 1));
                unbindTexture = true;
            } else {
                GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                this.shaderProgram.loadUniformBool("textured", false);
                this.shaderProgram.loadUniformVector3f("colour", new ImmutableVector3(1, 1, 1));
            }

            Matrix4f transformationMatrix = x.getParent().getTransform().createTransformationMatrix();
            this.shaderProgram.loadUniformMatrix("transformationMatrix", transformationMatrix);

            GL11.glDrawElements(GL11.GL_TRIANGLES, x.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            if (unbindTexture) {
                x.getTexture().unbind();
            }
            x.getVao().unbind();
        });
        this.finalizeRendering();
    }

    private void renderSphereColliders() {
        GameElement activeScene = this.gameManager.getActiveScene();
        this.unitSphere.bind();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        this.shaderProgram.loadUniformBool("textured", false);

        activeScene.flattened()
                .filter(x -> x.containsComponent(CollisionComponent.class))
                .map(x -> x.getComponent(CollisionComponent.class))
                .filter(x -> x instanceof SphereCollisionComponent)
                .forEach(x -> {
                    if (x.isColliding()) {
                        this.shaderProgram.loadUniformVector3f("colour", new ImmutableVector3(1, 0, 0));
                    } else {
                        this.shaderProgram.loadUniformVector3f("colour", new ImmutableVector3(0, 1, 0));
                    }
                    Matrix4f transformationMatrix = x.getTransform().createTransformationMatrix();
                    this.shaderProgram.loadUniformMatrix("transformationMatrix", transformationMatrix);
                    GL11.glDrawElements(GL11.GL_TRIANGLES, unitSphere.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                });
        this.unitSphere.unbind();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    private void renderBoxColliders() {
        GameElement activeScene = this.gameManager.getActiveScene();
        this.unitCube.bind();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        this.shaderProgram.loadUniformBool("textured", false);

        activeScene.flattened()
                .filter(x -> x.containsComponent(CollisionComponent.class))
                .map(x -> x.getComponent(CollisionComponent.class))
                .filter(x -> x instanceof BoxCollisionComponent)
                .forEach(x -> {
                    if (x.isColliding()) {
                        this.shaderProgram.loadUniformVector3f("colour", new ImmutableVector3(1, 0, 0));
                    } else {
                        this.shaderProgram.loadUniformVector3f("colour", new ImmutableVector3(0, 1, 0));
                    }
                    Matrix4f transformationMatrix = x.getTransform().createTransformationMatrix();
                    this.shaderProgram.loadUniformMatrix("transformationMatrix", transformationMatrix);
                    GL11.glDrawElements(GL11.GL_TRIANGLES, unitCube.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                });
        this.unitCube.unbind();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    @Override
    public void addRenderTarget(DebugRenderComponent renderComponent) {
        this.renderTargets.add(renderComponent);
    }

    @Override
    public void removeRenderTarget(DebugRenderComponent renderComponent) {
        this.renderTargets.remove(renderComponent);
    }

    @Override
    public boolean containsRenderTarget(DebugRenderComponent renderComponent) {
        return this.renderTargets.contains(renderComponent);
    }

    /**
     *
     */
    @Override
    public void initialize() {
        this.shaderProgram.start();
        super.shaderProgram.loadUniformMatrix("projectionMatrix", RenderEngine.getInstance().getProjectionMatrix());
        this.shaderProgram.stop();
    }

    @Override
    public void destroy() {
        this.renderTargets.forEach(x -> {
            x.setRenderer(null);
        });
        this.renderTargets = new HashSet<>();
        super.shaderProgram.destroy();
    }

    private void initializeRendering(Matrix4f viewMatrix) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.shaderProgram.start();
        this.shaderProgram.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    private void finalizeRendering() {
        this.shaderProgram.stop();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static DebugRenderer createDefault(GameManager gameManager) throws IOException {

        String vertexSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/testing/vertexShader.glsl");
        String fragmentSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/testing/fragmentShader.glsl");

        VertexShader vertexShader = new VertexShader("vs", vertexSource);
        FragmentShader fragmentShader = new FragmentShader("fs", fragmentSource);

        DebugRenderer dr = new DebugRenderer(gameManager, new ShaderProgram(vertexShader, fragmentShader) {
            @Override
            public void bindAttributes() {
                super.bindAttribute("position", VERTEX_POSITIONS.getValue());
            }
        });
        
        MeshDataObject md = MeshLoader.getInstance().loadResource("/blackengine/res/plane.obj");
        Vao vao = VaoLoader.loadVao(md);

        ImageDataObject image = ImageLoader.getInstance().loadResource("/blackengine/res/grid.png");
        Texture texture = TextureLoader.createTexture(image);

        MeshDataObject unitSphereMd = MeshLoader.getInstance().loadResource("/blackengine/res/unitSphere.obj");
        Vao unitSphere = VaoLoader.loadVao(unitSphereMd);

        MeshDataObject unitCubeMd = MeshLoader.getInstance().loadResource("/blackengine/res/unitCube.obj");
        Vao unitCube = VaoLoader.loadVao(unitCubeMd);

        dr.setUnitSphere(unitSphere);
        dr.setUnitCube(unitCube);

        return dr;
    }
}
