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

import blackengine.dataAccess.tools.PlainTextLoader;
import blackengine.gameLogic.components.prefab.rendering.TexturedMeshComponent;
import static blackengine.openGL.vao.vbo.AttributeType.NORMAL_VECTORS;
import static blackengine.openGL.vao.vbo.AttributeType.TEXTURE_COORDS;
import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_POSITIONS;
import blackengine.rendering.Camera;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.lighting.Light;
import blackengine.rendering.renderers.TargetPOVRenderer;
import blackengine.toolbox.math.ImmutableVector3;
import blackengine.toolbox.math.MatrixMath;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 */
public class TexturedMeshRenderer extends TargetPOVRenderer<TexturedMeshComponent> {

    protected Set<TexturedMeshComponent> targets;

    protected int maxLights = 6;

    protected TexturedMeshRenderer() {
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
    public void render(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        this.initializeRendering(viewMatrix);
        this.targets.forEach(x -> {
            x.getVao().bind();
            x.getTexture().bindToUnit(GL13.GL_TEXTURE0);
            Matrix4f transformationMatrix = x.getParent().getTransform().createTransformationMatrix();
            this.loadUniformMatrix("transformationMatrix", transformationMatrix);
            this.loadUniformLights(this.getLights(camera.getPosition()));
            GL11.glDrawElements(GL11.GL_TRIANGLES, x.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            x.getVao().unbind();
        });
        this.finalizeRendering();
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getValue(), "position");
        super.bindAttribute(TEXTURE_COORDS.getValue(), "texCoords");
        super.bindAttribute(NORMAL_VECTORS.getValue(), "normal");
    }

    @Override
    public void initialize() {
        this.start();
        super.loadUniformMatrix("projectionMatrix", super.getProjectionMatrix());
        this.stop();

    }
    
    protected List<Light> getLights(ImmutableVector3 position){
        return RenderEngine.getInstance()
                    .getLightStream()
                    .sorted((x,y) -> {
                        float distanceX = x.getPosition().distanceTo(position);
                        float distanceY = y.getPosition().distanceTo(position);
                        return Float.compare(distanceX, distanceY);
                    })
                    .limit(this.maxLights)
                    .collect(Collectors.toList());
    }

    protected void loadUniformLights(List<Light> lights) {
        int lightCount = 0;
        for (int i = 0; i < lights.size(); i++) {
            this.loadUniformVector3f("lightColour[" + i + "]", lights.get(i).getColour());
            this.loadUniformVector3f("lightPosition[" + i + "]", lights.get(i).getPosition());
            this.loadUniformVector3f("lightAttenuation[" + i + "]", lights.get(i).getAttenuation());
            lightCount++;
        }
        for (int i = lightCount; i < this.maxLights; i++) {
            this.loadUniformVector3f("lightColour[" + i + "]", new ImmutableVector3(0, 0, 0));
            this.loadUniformVector3f("lightPosition[" + i + "]", new ImmutableVector3(1000, 10000, 10000));
            this.loadUniformVector3f("lightAttenuation[" + i + "]", new ImmutableVector3(1, 1, 1));
        }

    }

    protected void initializeRendering(Matrix4f viewMatrix) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.start();
        this.loadUniformMatrix("viewMatrix", viewMatrix);
    }

    protected void finalizeRendering() {
        this.stop();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static TexturedMeshRenderer createDefault() throws IOException {
        TexturedMeshRenderer tmr = new TexturedMeshRenderer();

        String vertexSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/texturedRendering/vertexShader.glsl");
        String fragmentSource = PlainTextLoader.loadResource("/blackengine/rendering/prefab/texturedRendering/fragmentShader.glsl");

        tmr.load(vertexSource, fragmentSource);

        return tmr;
    }

    public static TexturedMeshRenderer createEmpty() {
        return new TexturedMeshRenderer();
    }

}
