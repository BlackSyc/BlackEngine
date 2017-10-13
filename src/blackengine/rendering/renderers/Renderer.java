/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.renderers;

import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 *
 * @author Blackened
 * @param <S>
 * @param <M>
 */
public abstract class Renderer<S extends ShaderProgram, M extends Material<S>> {

    protected final S shaderProgram;

    private final HashSet<RenderComponent<S, M>> targets;

    private final float renderPriority;

    public float getRenderPriority() {
        return renderPriority;
    }

    
    public final Stream<RenderComponent<S, M>> getTargets() {
        return this.targets.stream();
    }

    @SuppressWarnings("unchecked")
    public final Class<S> getShaderClass() {
        return (Class<S>) this.shaderProgram.getClass();
    }

    public Renderer(S shaderProgram) {
        this(shaderProgram, 1);
    }

    public Renderer(S shaderProgram, float renderPriority) {
        this.shaderProgram = shaderProgram;
        this.targets = new HashSet<>();
        this.renderPriority = renderPriority;
    }

    public abstract void render();

    public void addTarget(RenderComponent<S, M> target) {
        this.targets.add(target);
    }

    public boolean containsTarget(RenderComponent<S, M> target) {
        return this.targets.contains(target);
    }

    public void removeTarget(RenderComponent<S, M> target) {
        this.targets.remove(target);
    }

    public void destroy() {

    }
}
