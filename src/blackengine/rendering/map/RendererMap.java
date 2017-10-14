/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.map;

import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import blackengine.rendering.renderers.Material;
import blackengine.rendering.renderers.Renderer;
import blackengine.rendering.renderers.ShaderProgramBase;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Contains all renderers in an ordered fashion, and mapped by the class of the
 * shader they use.
 *
 * @author Blackened
 */
public class RendererMap {

    /**
     * All entries containing the renderers mapped by the class of the shader
     * they use.
     */
    private final ArrayList<RendererEntry> entries;

    /**
     * A comparator that is used to order the renderers.
     */
    private final Comparator<RendererEntry> comparator;

    /**
     * Default constructor for creating a new instance of RendererMap. Creates a
     * default comparator that uses the Float.compare method.
     */
    public RendererMap() {
        this.entries = new ArrayList<>();
        this.comparator = (x, y)
                -> Float.compare(
                        x.getRenderer().getRenderPriority(),
                        y.getRenderer().getRenderPriority());
    }

    /**
     * Constructor for creating a new instance of RendererMap.
     *
     * @param comparator The comparator that will be used to order the internal
     * renderer entries.
     */
    public RendererMap(Comparator<RendererEntry> comparator) {
        this.entries = new ArrayList<>();
        this.comparator = comparator;
    }

    /**
     * Getter for an ordered stream of all renderers.
     *
     * @return An instance of a Stream of renderers that is ordered according to
     * the internal comparator.
     */
    public Stream<Renderer> getRenderers() {
        return this.entries.stream().map(x -> x.getRenderer());
    }

    /**
     * Puts a new renderer in this instance of renderer map. If one using the
     * same shader class was already present, that one will be destroyed and
     * replaced with the new one. The render targets that were present in the
     * old renderer will be transferred to the new renderer.
     *
     * @param <S>
     * @param <M>
     * @param renderer The renderer to be added to this renderer map.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> void put(Renderer<S, M> renderer) {
        Class<S> shaderClass = renderer.getShaderClass();
        if (this.containsRendererFor(shaderClass)) {
            Stream<RenderComponent<S, Material<S>>> renderComponents = this.get(shaderClass).getTargets();
            renderComponents
                    .map(x -> {
                        @SuppressWarnings("unchecked")
                        RenderComponent<S, M> target = (RenderComponent<S, M>) x;
                        return target;
                    })
                    .forEach(x -> renderer.addTarget(x));
            this.destroyRendererFor(renderer.getShaderClass());

        }
        RendererEntry<S, M> entry = new RendererEntry<>(shaderClass, renderer);
        this.entries.add(entry);
        this.entries.sort(this.comparator);
    }

    /**
     * Destroys and removes the renderer using the specified shader class, if
     * any.
     *
     * @param shaderClass The class of the shader used by the renderer that is
     * to be removed.
     */
    public void destroyRendererFor(Class<? extends ShaderProgramBase> shaderClass) {
        if (this.containsRendererFor(shaderClass)) {
            this.get(shaderClass).destroy();
            this.entries.removeIf(x -> x.getShaderClass().equals(shaderClass));
        }
    }

    /**
     * Retrieves the renderer using the specified shader class, if any.
     *
     * @param <S>
     * @param <M>
     * @param shaderClass The class of the shader that is used by the renderer.
     * @return An instance of Renderer, or null if none was found.
     */
    @SuppressWarnings("unchecked")
    public <S extends ShaderProgramBase, M extends Material<S>> Renderer<S, M> get(Class<S> shaderClass) {
        return this.entries.stream()
                .filter(x -> x.getShaderClass().equals(shaderClass))
                .findFirst()
                .orElse(new NullEntry())
                .getRenderer();
    }

    /**
     * Checks whether a renderer using the specified shader class is present.
     *
     * @param shaderClass The class of the shader the renderer should be using.
     * @return True if a renderer using the specified shader class is present,
     * false otherwise.
     */
    public boolean containsRendererFor(Class<? extends ShaderProgramBase> shaderClass) {
        return this.entries.stream()
                .anyMatch(x -> x.getShaderClass().equals(shaderClass));
    }

    /**
     * Checks whether a renderer is present that is compatible with the
     * specified render component.
     *
     * @param <S>
     * @param <M>
     * @param renderComponent The render component for which it will be checked
     * if a compatible renderer is present.
     * @return True if a renderer was found that is compatible with the render
     * component, false otherwise.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> boolean containsRendererFor(RenderComponent<S, M> renderComponent) {
        return this.containsRendererFor(renderComponent.getMaterial().getShaderClass());
    }

    /**
     * Retrieves the renderer that is compatible with rendering the specified
     * instance of render component, if any.
     *
     * @param <S>
     * @param <M>
     * @param renderComponent The render component for which a compatible
     * renderer will be found, if any.
     * @return A renderer that is compatible with the specified render
     * component, or null if none was found.
     */
    public <S extends ShaderProgramBase, M extends Material<S>> Renderer<S, M> getRendererFor(RenderComponent<S, M> renderComponent) {
        return this.get(renderComponent.getMaterial().getShaderClass());
    }

    /**
     * Calls the destroy method on each of the renderers this instance contains,
     * and clears the reference to them.
     */
    public void destroy() {
        this.entries.forEach(x -> x.getRenderer().destroy());
        this.entries.clear();
    }

}
