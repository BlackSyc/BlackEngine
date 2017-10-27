/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.pipeline.elements;

import blackengine.rendering.pipeline.shaderPrograms.Material;
import blackengine.rendering.pipeline.shaderPrograms.MaterialShaderProgram;
import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import blackengine.rendering.Camera;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 *
 * @author Blackened
 * @param <S>
 * @param <M>
 */
public class Renderer<S extends MaterialShaderProgram, M extends Material<S>> implements PipelineElement<S> {

    /**
     * The shader program that is used to render this renderers targets.
     */
    protected final S shaderProgram;

    /**
     * The targets that are to be rendered.
     */
    private final HashSet<RenderComponent<S, M>> targets;

    /**
     * The priority index used to determine the order of renderers.
     */
    private final float renderPriority;

    /**
     * Flags whether this instance of renderer was destroyed.
     */
    private boolean destroyed = false;

    private boolean enabled = true;

    public S getShaderProgram(){
        return this.shaderProgram;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for whether this instance of renderer was destroyed.
     *
     * @return True if this renderer was destroyed, false otherwise.
     */
    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Getter for the render priority of this instance.
     *
     * @return A float determining the priority index used to determine the
     * order of renderers.
     */
    @Override
    public float getPriority() {
        return renderPriority;
    }

    /**
     * Getter for the targets that are to be rendered by this instance of
     * Renderer.
     *
     * @return A Stream of all targets that will have to be rendered by this
     * renderer.
     */
    public final Stream<RenderComponent<S, M>> getTargets() {
        return this.targets.stream();
    }

    /**
     * Returns the class of the shader program this renderer is using.
     *
     * @return The class of the shader program this renderer is using to render
     * its targets..
     */
    @SuppressWarnings("unchecked")
    @Override
    public final Class<S> getShaderClass() {
        return (Class<S>) this.shaderProgram.getClass();
    }

    /**
     * Default constructor for creating a new instance of Renderer. The render
     * priority will be set to 1.
     *
     * @param shaderProgram The shader program that will be used in this
     * renderer.
     */
    public Renderer(S shaderProgram) {
        this(shaderProgram, 1);
    }

    /**
     * Constructor for creating a new instance of Renderer.
     *
     * @param shaderProgram The shader program that will be used in this
     * renderer.
     * @param renderPriority A float determining the priority index used to
     * determine the order in the pipeline. A higher priority means it will be
     * rendered earlier in the pipeline.
     */
    public Renderer(S shaderProgram, float renderPriority) {
        this.shaderProgram = shaderProgram;
        this.targets = new HashSet<>();
        this.renderPriority = renderPriority;
    }

    /**
     * Initializes this renderer and its shader program.
     */
    @Override
    public void initialize() {
        this.shaderProgram.initialize();
    }

    /**
     * Renders all targets using the shader program.
     * @param camera
     */
    @SuppressWarnings("unchecked")
    @Override
    public void render(Camera camera) {
        if (this.isEnabled()) {
            
            this.shaderProgram.start(camera);
            this.shaderProgram.applySettings();
            this.shaderProgram.loadFrameUniforms();
            this.getTargets().forEach(x -> {
                this.shaderProgram.loadTransformUniforms(x.getParent().getTransform());

                this.shaderProgram.loadMaterialUniforms(x.getMaterial());
                this.shaderProgram.draw(x.getVao());
            });

            this.shaderProgram.revertSettings();
            this.shaderProgram.stop();
            
        }
    }

    /**
     * Adds a new target to this renderer that will be rendered. If this target
     * is already being rendered, it will not be added.
     *
     * @param target The target that will be rendered.
     */
    public void addTarget(RenderComponent<S, M> target) {
        this.targets.add(target);
    }

    /**
     * Checks whether the specified target is being rendered by this renderer.
     *
     * @param target The target for which to check its presence in this
     * renderer.
     * @return True if the target is present in this renderer, false otherwise.
     */
    public boolean containsTarget(RenderComponent<S, M> target) {
        return this.targets.contains(target);
    }

    /**
     * Removes a target from this renderer so that it will no longer be
     * rendered.
     *
     * @param target The target that is to be removed from this renderer.
     */
    public void removeTarget(RenderComponent<S, M> target) {
        this.targets.remove(target);
    }

    /**
     * Destroys this renderers shader program, deactivates all targets, and
     * flags this renderer for destruction.
     */
    @Override
    public void destroy() {
        this.shaderProgram.destroy();
        this.targets.stream().forEach(x -> x.deactivate());
        this.destroyed = true;
    }
}
