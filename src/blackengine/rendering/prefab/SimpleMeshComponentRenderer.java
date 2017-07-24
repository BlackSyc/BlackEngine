/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.prefab;

import blackengine.gameLogic.components.prefab.SimpleMeshRenderComponent;
import blackengine.openGL.texture.Texture;
import static blackengine.openGL.vao.vbo.AttributeType.*;
import blackengine.rendering.ComponentRenderer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 */
public class SimpleMeshComponentRenderer extends ComponentRenderer<SimpleMeshRenderComponent> {

    private HashMap<Texture, Set<SimpleMeshRenderComponent>> renderTargets;

    public SimpleMeshComponentRenderer() {
        super("/blackengine/rendering/prefab/vertexShader.glsl", "/blackengine/rendering/prefab/fragmentShader.glsl");
        this.renderTargets = new HashMap<>();
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

    @Override
    public void render(Matrix4f viewMatrix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addRenderTarget(SimpleMeshRenderComponent renderComponent) {
        if (!this.renderTargets.containsKey(renderComponent.getTexture())) {
            this.renderTargets.put(renderComponent.getTexture(), new HashSet<>());
        }
        this.renderTargets.get(renderComponent.getTexture()).add(renderComponent);
    }

    @Override
    public void removeRenderTarget(SimpleMeshRenderComponent renderComponent) {
        if (this.renderTargets.containsKey(renderComponent.getTexture())) {
            this.renderTargets.get(renderComponent.getTexture()).remove(renderComponent);
        }
    }

    @Override
    public boolean containsRenderTarget(SimpleMeshRenderComponent renderComponent) {
        return this.renderTargets.values().stream().anyMatch(x -> x.contains(renderComponent));
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(VERTEX_POSITIONS.getValue(), "position");
        super.bindAttribute(TEXTURE_COORDS.getValue(), "textureCoords");
        super.bindAttribute(NORMAL_VECTORS.getValue(), "normal");
    }

}
