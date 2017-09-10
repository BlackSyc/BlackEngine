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
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class DebugRenderer extends TargetPOVRenderer<DebugRenderComponent> {

    private Set<DebugRenderComponent> renderTargets;

    private Entity grid;

    private boolean gridEnabled = false;

    public void setGrid(Entity grid) {
        this.grid = grid;
    }

    public boolean isGridEnabled() {
        return gridEnabled;
    }

    public void setGridEnabled(boolean gridEnabled) {
        this.gridEnabled = gridEnabled;
    }

    protected DebugRenderer() {
        this.renderTargets = new HashSet<>();
    }

    @Override
    public void render(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        this.initializeRendering(viewMatrix);

        if (this.gridEnabled) {
            float cameraDistance = camera.getPosition().length();
            this.renderGrid(cameraDistance);
        }

        this.renderTargets.forEach(x -> {
            x.getVao().bind();

            boolean unbindTexture = false;
            if (!x.isWireFrameEnabled()) {
                GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                x.getTexture().bindToUnit(GL13.GL_TEXTURE0);
                this.loadUniformBool("textured", true);
                unbindTexture = true;
            } else {
                GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                this.loadUniformBool("textured", false);
            }

            Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(x.getParent().getTransform().getAbsolutePosition(), x.getParent().getTransform().getEulerRotation(), 1);
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
        this.grid.getTransform().setScale(new Vector3f(cameraDistance, cameraDistance, cameraDistance));
        
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
            }

            Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(
                    this.grid.getTransform().getAbsolutePosition(), 
                    this.grid.getTransform().getEulerRotation(), 
                    this.grid.getTransform().getScale());
            this.loadUniformMatrix("transformationMatrix", transformationMatrix);

            GL11.glDrawElements(GL11.GL_TRIANGLES, meshComp.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            if (unbindTexture) {
                meshComp.getTexture().unbind();
            }
            meshComp.getVao().unbind();
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

    public static DebugRenderer createDefault() throws IOException {
        DebugRenderer tmr = new DebugRenderer();

        String vertexSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/testing/vertexShader.glsl");
        String fragmentSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/testing/fragmentShader.glsl");

        tmr.load(vertexSource, fragmentSource);
        
        Entity grid = new Entity("debug_grid");
        
        MeshDataObject md = MeshLoader.getInstance().loadResource("/blackengine/res/plane.obj");
        Vao vao = VaoLoader.loadVAO(md);
        
        ImageDataObject image = ImageLoader.getInstance().loadResource("/blackengine/res/grid.png");
        Texture texture = TextureLoader.createTexture(image);
                
        
        
        DebugRenderComponent tmc = new DebugRenderComponent(vao, tmr);
        tmc.setTexture(texture);
        tmc.setWireFrameEnabled(false);
        grid.addComponent(tmc);
        
        tmr.setGrid(grid);

        return tmr;
    }

    public static DebugRenderer createEmpty() {
        return new DebugRenderer();
    }
}
