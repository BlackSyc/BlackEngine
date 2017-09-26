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
import blackengine.gameLogic.Entity;
import blackengine.gameLogic.GameElement;
import blackengine.gameLogic.GameManager;
import blackengine.gameLogic.components.prefab.collision.BoxCollisionComponent;
import blackengine.gameLogic.components.prefab.collision.CollisionComponent;
import blackengine.gameLogic.components.prefab.rendering.DebugRenderComponent;
import blackengine.openGL.texture.Texture;
import blackengine.openGL.texture.TextureLoader;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.VaoLoader;
import static blackengine.openGL.vao.vbo.AttributeType.*;
import blackengine.rendering.Camera;
import blackengine.rendering.renderers.TargetPOVRenderer;
import blackengine.toolbox.math.MatrixMath;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class DebugRenderer extends TargetPOVRenderer<DebugRenderComponent> {

    private Set<DebugRenderComponent> renderTargets;

    private Entity grid;

    private Vao unitCube;

    private boolean gridEnabled = false;

    private boolean renderCollidersEnabled = false;

    private GameManager gameManager;

    public void setGrid(Entity grid) {
        this.grid = grid;
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

    protected DebugRenderer(GameManager gameManager) {

        this.gameManager = gameManager;
        this.renderTargets = new HashSet<>();
    }

    @Override
    public void render(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        this.initializeRendering(viewMatrix);

        if (this.gridEnabled) {
            Vector2f horizontalCameraTranslation = new Vector2f(camera.getPosition().x, camera.getPosition().z);
            float cameraDistance = horizontalCameraTranslation.length();
            this.renderGrid(cameraDistance);
        }

        if (this.renderCollidersEnabled) {
            this.renderColliders();
        }

        this.renderTargets.forEach(x -> {
            x.getVao().bind();

            boolean unbindTexture = false;
            if (!x.isWireFrameEnabled()) {
                GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                x.getTexture().bindToUnit(GL13.GL_TEXTURE0);
                this.loadUniformBool("textured", true);
                this.loadUniformVector3f("colour", new Vector3f(1,1,1));
                unbindTexture = true;
            } else {
                GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                this.loadUniformBool("textured", false);
                this.loadUniformVector3f("colour", new Vector3f(1,1,1));
            }

            Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(x.getParent().getTransform().getAbsolutePosition(), x.getParent().getTransform().getAbsoluteEulerRotation(), x.getParent().getTransform().getAbsoluteScale());
            this.loadUniformMatrix("transformationMatrix", transformationMatrix);

            GL11.glDrawElements(GL11.GL_TRIANGLES, x.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            if (unbindTexture) {
                x.getTexture().unbind();
            }
            x.getVao().unbind();
        });
        this.finalizeRendering();
    }

    private void renderGrid(float cameraDistance) {
        DebugRenderComponent meshComp = this.grid.getComponent(DebugRenderComponent.class);
        this.grid.getTransform().setAbsoluteScale(new Vector3f(cameraDistance, cameraDistance, cameraDistance));

        meshComp.getVao().bind();

        boolean unbindTexture = false;
        if (!meshComp.isWireFrameEnabled()) {
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            meshComp.getTexture().bindToUnit(GL13.GL_TEXTURE0);
            this.loadUniformBool("textured", true);
            unbindTexture = true;
        } else {
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            this.loadUniformBool("textured", false);
            this.loadUniformVector3f("colour", new Vector3f(1,1,1));
        }

        Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(
                this.grid.getTransform().getAbsolutePosition(),
                this.grid.getTransform().getAbsoluteEulerRotation(),
                this.grid.getTransform().getAbsoluteScale());
        this.loadUniformMatrix("transformationMatrix", transformationMatrix);

        GL11.glDrawElements(GL11.GL_TRIANGLES, meshComp.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        if (unbindTexture) {
            meshComp.getTexture().unbind();
        }
        meshComp.getVao().unbind();
    }

    private void renderColliders() {
        GameElement activeScene = this.gameManager.getActiveScene();
        this.unitCube.bind();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        this.loadUniformBool("textured", false);
        this.loadUniformVector3f("colour", new Vector3f(0,1,0));
        activeScene.flattened().filter(x -> x.containsComponent(CollisionComponent.class)).map(x -> x.getComponent(CollisionComponent.class)).forEach(x -> {
            if (x instanceof BoxCollisionComponent) {
                Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(x.getTransform().getAbsolutePosition(), x.getTransform().getAbsoluteEulerRotation(), x.getTransform().getAbsoluteScale());
                this.loadUniformMatrix("transformationMatrix", transformationMatrix);
                GL11.glDrawElements(GL11.GL_TRIANGLES, unitCube.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        });
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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.start();
        this.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    private void finalizeRendering() {
        this.stop();
        GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static DebugRenderer createDefault(GameManager gameManager) throws IOException {
        DebugRenderer tmr = new DebugRenderer(gameManager);

        String vertexSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/testing/vertexShader.glsl");
        String fragmentSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/testing/fragmentShader.glsl");

        tmr.load(vertexSource, fragmentSource);

        Entity grid = new Entity("debug_grid");

        MeshDataObject md = MeshLoader.getInstance().loadResource("/blackengine/res/plane.obj");
        Vao vao = VaoLoader.loadVao(md);

        ImageDataObject image = ImageLoader.getInstance().loadResource("/blackengine/res/grid.png");
        Texture texture = TextureLoader.createTexture(image);

        MeshDataObject unitCubeMd = MeshLoader.getInstance().loadResource("/blackengine/res/unitCube.obj");
        Vao unitCube = VaoLoader.loadVao(unitCubeMd);

        DebugRenderComponent tmc = new DebugRenderComponent(vao, tmr);
        tmc.setTexture(texture);
        tmc.setWireFrameEnabled(false);
        grid.addComponent(tmc);

        tmr.setGrid(grid);
        tmr.setUnitCube(unitCube);

        return tmr;
    }

    public static DebugRenderer createEmpty(GameManager gameManager) {
        return new DebugRenderer(gameManager);
    }
}
