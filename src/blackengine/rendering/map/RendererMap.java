/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.map;

import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import blackengine.rendering.renderers.PipelineElement;
import blackengine.rendering.renderers.Processor;
import blackengine.rendering.renderers.shaderPrograms.Material;
import blackengine.rendering.renderers.Renderer;
import blackengine.rendering.renderers.shaderPrograms.MaterialShaderProgram;
import blackengine.rendering.renderers.shaderPrograms.ProcessingShaderProgram;
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
    private final ArrayList<RendererEntry> rendererEntries;
    
    private final ArrayList<ProcessorEntry> processorEntries;
    
    private final ArrayList<PipelineElement> pipeline;

    /**
     * A comparator that is used to order the renderers.
     */
    private final Comparator<PipelineElement> comparator;

    /**
     * Default constructor for creating a new instance of RendererMap. Creates a
     * default comparator that uses the Float.compare method.
     */
    public RendererMap() {
        this.rendererEntries = new ArrayList<>();
        this.processorEntries = new ArrayList<>();
        this.pipeline = new ArrayList<>();
        this.comparator = (x, y)
                -> Float.compare(
                        x.getPriority(),
                        y.getPriority());
    }

    /**
     * Getter for an ordered stream of all renderers.
     *
     * @return An instance of a Stream of renderers that is ordered according to
     * the internal comparator.
     */
    public Stream<PipelineElement> getPipeline() {
        return this.pipeline.stream();
    }
    
    public void removeDestroyedPipelineElements(){
        this.rendererEntries.removeIf(x -> x.getRenderer().isDestroyed());
        this.processorEntries.removeIf(x -> x.getProcessor().isDestroyed());
        this.pipeline.removeIf(x -> x.isDestroyed());
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
    public <S extends MaterialShaderProgram, M extends Material<S>> void put(Renderer<S, M> renderer) {
        Class<S> shaderClass = renderer.getShaderClass();
        if (this.containsRendererFor(shaderClass)) {
            Stream<RenderComponent<S, Material<S>>> renderComponents = this.getRenderer(shaderClass).getTargets();
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
        this.rendererEntries.add(entry);
        
        // add the renderer to the pipeline as well.
        this.pipeline.add(renderer);
        this.pipeline.sort(this.comparator);
        
    }

    public <S extends ProcessingShaderProgram> void put(Processor<S> processor){
        Class<S> shaderClass = processor.getShaderClass();
        if(this.containsProcessorFor(shaderClass)){
            this.destroyProcessorFor(shaderClass);
        }
        
        ProcessorEntry<S> entry = new ProcessorEntry<>(shaderClass, processor);
        this.processorEntries.add(entry);
        
        // add the processor to the pipeline as well.
        this.pipeline.add(processor);
        this.pipeline.sort(this.comparator);
    }
    
    public <S extends ProcessingShaderProgram> boolean containsProcessorFor(Class<S> shaderProgramClass){
        return this.processorEntries.stream()
                .anyMatch(x -> x.getShaderClass().equals(shaderProgramClass));
    }
    
    public <S extends ProcessingShaderProgram> void destroyProcessorFor(Class<S> shaderProgramClass){
        if(this.containsProcessorFor(shaderProgramClass)){
            this.getProcessor(shaderProgramClass).destroy();
            this.processorEntries.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
            this.pipeline.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
        }
    }
    
    @SuppressWarnings("unchecked")
    public <S extends ProcessingShaderProgram> Processor<S> getProcessor(Class<S> shaderProgramClass){
                return this.processorEntries.stream()
                .filter(x -> x.getShaderClass().equals(shaderProgramClass))
                .findFirst()
                .orElse(new NullProcessorEntry())
                .getProcessor();
    }
    /**
     * Destroys and removes the renderer using the specified shader class, if
     * any.
     *
     * @param shaderClass The class of the shader used by the renderer that is
     * to be removed.
     */
    public void destroyRendererFor(Class<? extends MaterialShaderProgram> shaderClass) {
        if (this.containsRendererFor(shaderClass)) {
            this.getRenderer(shaderClass).destroy();
            this.rendererEntries.removeIf(x -> x.getShaderClass().equals(shaderClass));
            this.pipeline.removeIf(x -> x.getShaderClass().equals(shaderClass));
        }
    }

    /**
     * Removes a specified renderer from this renderer map, if it was present.
     *
     * @param renderer The renderer that is to be removed if it is present in
     * the renderer map.
     */
    public void removeRenderer(Renderer renderer) {
        this.rendererEntries.removeIf(x -> x.getShaderClass().equals(renderer.getShaderClass()));
        this.pipeline.removeIf(x -> x.getShaderClass().equals(renderer.getShaderClass()));
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
    public <S extends MaterialShaderProgram, M extends Material<S>> Renderer<S, M> getRenderer(Class<S> shaderClass) {
        return this.rendererEntries.stream()
                .filter(x -> x.getShaderClass().equals(shaderClass))
                .findFirst()
                .orElse(new NullRendererEntry())
                .getRenderer();
    }

    /**
     * Checks whether a renderer using the specified shader class is present.
     *
     * @param shaderClass The class of the shader the renderer should be using.
     * @return True if a renderer using the specified shader class is present,
     * false otherwise.
     */
    public boolean containsRendererFor(Class<? extends MaterialShaderProgram> shaderClass) {
        return this.rendererEntries.stream()
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
    public <S extends MaterialShaderProgram, M extends Material<S>> boolean containsRendererFor(RenderComponent<S, M> renderComponent) {
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
    public <S extends MaterialShaderProgram, M extends Material<S>> Renderer<S, M> getRendererFor(RenderComponent<S, M> renderComponent) {
        return this.getRenderer(renderComponent.getMaterial().getShaderClass());
    }

    /**
     * Calls the destroy method on each of the renderers this instance contains,
     * and clears the reference to them.
     */
    public void destroy() {
        this.rendererEntries.forEach(x -> x.getRenderer().destroy());
        this.rendererEntries.clear();
    }

}
