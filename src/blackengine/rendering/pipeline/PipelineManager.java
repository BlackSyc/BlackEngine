/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.pipeline;

import blackengine.rendering.pipeline.elements.PipelineElement;
import blackengine.rendering.pipeline.map.NullProcessorEntry;
import blackengine.rendering.pipeline.map.NullRendererEntry;
import blackengine.rendering.pipeline.map.ProcessorEntry;
import blackengine.rendering.pipeline.map.RendererEntry;
import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import blackengine.rendering.pipeline.elements.Processor;
import blackengine.rendering.pipeline.shaderPrograms.Material;
import blackengine.rendering.pipeline.elements.Renderer;
import blackengine.rendering.pipeline.shaderPrograms.MaterialShaderProgram;
import blackengine.rendering.pipeline.shaderPrograms.ProcessingShaderProgram;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Contains all renderers in an ordered fashion, and mapped by the class of the
 * shader they use.
 *
 * @author Blackened
 */
public class PipelineManager {

    //<editor-fold desc="Fields" defaultstate="collapsed">
    /**
     * All entries containing the renderers mapped by the class of the shader
     * they use. Used internally only.
     */
    private final ArrayList<RendererEntry> rendererEntries;

    /**
     * All entries containing the processors mapped by the class of the shader
     * they use. Used internally only.
     */
    private final ArrayList<ProcessorEntry> processorEntries;

    /**
     * The full sorted pipeline used for rendering containing all processors and
     * renderers in the order of their priority. Exposed through member
     * {@link #getPipeline()}.
     */
    private final ArrayList<PipelineElement> pipeline;

    /**
     * The comparator that is used to order the pipeline. Used internally only.
     */
    private final Comparator<PipelineElement> comparator;
    //</editor-fold>

    //<editor-fold desc="Getters & Setters" defaultstate="collapsed">
    /**
     * Getter for stream of the rendering pipeline, ordered by their rendering
     * priority.
     *
     * @return An instance of a Stream of pipeline elements that is ordered
     * according to the rendering priority.
     */
    public Stream<PipelineElement> getPipeline() {
        return this.pipeline.stream();
    }
    //</editor-fold>

    //<editor-fold desc="Constructors" defaultstate="collapsed">
    /**
     * Default constructor for creating a new instance of RendererMap. Creates a
     * default comparator that uses the Float.compare method.
     */
    public PipelineManager() {
        this.rendererEntries = new ArrayList<>();
        this.processorEntries = new ArrayList<>();
        this.pipeline = new ArrayList<>();
        this.comparator = (x, y)
                -> Float.compare(
                        x.getPriority(),
                        y.getPriority());
    }
    //</editor-fold>

    //<editor-fold desc="Public Methods" defaultstate="collapsed">
    //<editor-fold desc="Common" defaultstate="collapsed">
    /**
     * Removes all destroyed elements of the pipeline.
     */
    public void removeDestroyedPipelineElements() {
        this.rendererEntries.removeIf(x -> x.getRenderer().isDestroyed());
        this.processorEntries.removeIf(x -> x.getProcessor().isDestroyed());
        this.pipeline.removeIf(x -> x.isDestroyed());
    }

    /**
     * Calls the destroy method on each of the renderers this instance contains,
     * and clears the reference to them.
     */
    public void destroy() {
        this.getPipeline().forEach(x -> x.destroy());
        this.rendererEntries.clear();
        this.processorEntries.clear();
        this.pipeline.clear();
    }
    //</editor-fold>

    //<editor-fold desc="Renderers" defaultstate="collapsed">
    /**
     * Puts a new renderer in the pipeline. If one using the same shader program
     * class was already present, that one will be destroyed and replaced with
     * the new one. The render targets that were present in the old renderer
     * will be transferred to the new renderer.
     *
     * @param <S>
     * @param <M>
     * @param renderer The renderer to be added to this renderer map.
     */
    public <S extends MaterialShaderProgram, M extends Material<S>> void put(Renderer<S, M> renderer) {
        Class<S> shaderClass = renderer.getShaderClass();
        if (this.containsRendererWith(shaderClass)) {
            Stream<RenderComponent<S, Material<S>>> renderComponents = this.getRendererWith(shaderClass).getTargets();
            renderComponents
                    .map(x -> {
                        @SuppressWarnings("unchecked")
                        RenderComponent<S, M> target = (RenderComponent<S, M>) x;
                        return target;
                    })
                    .forEach(x -> renderer.addTarget(x));
            this.destroyRendererWith(renderer.getShaderClass());

        }

        // Add the renderer to the pipeline.
        this.pipeline.add(renderer);
        this.pipeline.sort(this.comparator);

        // Add the renderer to the internal renderer map as well.
        RendererEntry<S, M> entry = new RendererEntry<>(shaderClass, renderer);
        this.rendererEntries.add(entry);
        
        renderer.initialize();
    }

    /**
     * Destroys and removes the renderer using the specified shader class, if
     * any.
     *
     * @param shaderProgramClass The class of the shader program used by the
     * renderer that is to be removed.
     */
    public void destroyRendererWith(Class<? extends MaterialShaderProgram> shaderProgramClass) {
        if (this.containsRendererWith(shaderProgramClass)) {
            this.getRendererWith(shaderProgramClass).destroy();
            this.rendererEntries.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
            this.pipeline.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
        }
    }

    /**
     * Destroys and removes the renderer that can be used to render the
     * specified component.
     *
     * @param <S>
     * @param <M>
     * @param renderComponent The render component that will be used to look up
     * the compatible renderer.
     */
    public <S extends MaterialShaderProgram, M extends Material<S>> void destroyRendererFor(RenderComponent<S, M> renderComponent) {
        Class<? extends MaterialShaderProgram> shaderProgramClass = renderComponent.getMaterial().getShaderClass();
        if (this.containsRendererWith(shaderProgramClass)) {
            this.getRendererWith(shaderProgramClass).destroy();
            this.rendererEntries.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
            this.pipeline.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
        }
    }

    /**
     * Retrieves the renderer using the specified shader program class, if any.
     *
     * @param <S>
     * @param <M>
     * @param shaderProgramClass The class of the shader program that is used by
     * the renderer.
     * @return An instance of Renderer, or null if none was found.
     */
    @SuppressWarnings("unchecked")
    public <S extends MaterialShaderProgram, M extends Material<S>> Renderer<S, M> getRendererWith(Class<S> shaderProgramClass) {
        return this.rendererEntries.stream()
                .filter(x -> x.getShaderClass().equals(shaderProgramClass))
                .findFirst()
                .orElse(new NullRendererEntry())
                .getRenderer();
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
        return this.getRendererWith(renderComponent.getMaterial().getShaderClass());
    }

    /**
     * Checks whether a renderer using the specified shader program class is
     * present.
     *
     * @param shaderProgramClass The class of the shader program the renderer
     * should be using.
     * @return True if a renderer using the specified shader class is present,
     * false otherwise.
     */
    public boolean containsRendererWith(Class<? extends MaterialShaderProgram> shaderProgramClass) {
        return this.rendererEntries.stream()
                .anyMatch(x -> x.getShaderClass().equals(shaderProgramClass));
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
        return this.containsRendererWith(renderComponent.getMaterial().getShaderClass());
    }
    //</editor-fold>

    //<editor-fold desc="Processors" defaultstate="collapsed">
    /**
     * Puts a new processor in the pipeline. If one using the same shader
     * program class was already present, that one will be destroyed and
     * replaced with the new one.
     *
     * @param <S>
     * @param processor The processor that will be added to the pipeline.
     */
    public <S extends ProcessingShaderProgram> void put(Processor<S> processor) {
        Class<S> shaderClass = processor.getShaderClass();
        if (this.containsProcessorWith(shaderClass)) {
            this.destroyProcessorWith(shaderClass);
        }

        // add the processor to the pipeline.
        this.pipeline.add(processor);
        this.pipeline.sort(this.comparator);

        // Add the processor to the internal processor map as well.
        ProcessorEntry<S> entry = new ProcessorEntry<>(shaderClass, processor);
        this.processorEntries.add(entry);
        
        processor.initialize();
    }

    /**
     * Destroys and removes the processor using the specified shader class, if
     * any.
     *
     * @param <S>
     * @param shaderProgramClass The class of the shader program used by the
     * processor that is to be removed.
     */
    public <S extends ProcessingShaderProgram> void destroyProcessorWith(Class<S> shaderProgramClass) {
        if (this.containsProcessorWith(shaderProgramClass)) {
            this.getProcessor(shaderProgramClass).destroy();
            this.processorEntries.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
            this.pipeline.removeIf(x -> x.getShaderClass().equals(shaderProgramClass));
        }
    }

    /**
     * Retrieves the processor using the specified shader program class, if any.
     *
     * @param <S>
     * @param shaderProgramClass The class of the shader program that is used by
     * the processor.
     * @return An instance of Processor, or null if none was found.
     */
    @SuppressWarnings("unchecked")
    public <S extends ProcessingShaderProgram> Processor<S> getProcessor(Class<S> shaderProgramClass) {
        return this.processorEntries.stream()
                .filter(x -> x.getShaderClass().equals(shaderProgramClass))
                .findFirst()
                .orElse(new NullProcessorEntry())
                .getProcessor();
    }

    /**
     * Checks whether a processor using the specified shader program class is
     * present.
     *
     * @param shaderProgramClass The class of the shader program the processor
     * should be using.
     * @return True if a processor using the specified shader class is present,
     * false otherwise.
     */
    public boolean containsProcessorWith(Class<? extends ProcessingShaderProgram> shaderProgramClass) {
        return this.processorEntries.stream()
                .anyMatch(x -> x.getShaderClass().equals(shaderProgramClass));
    }
    //</editor-fold>
    //</editor-fold>
}
