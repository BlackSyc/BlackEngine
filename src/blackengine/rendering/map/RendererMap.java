/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.map;

import blackengine.gameLogic.components.prefab.rendering.RenderComponent;
import blackengine.rendering.renderers.Material;
import blackengine.rendering.renderers.Renderer;
import blackengine.rendering.renderers.ShaderProgram;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 *
 * @author Blackened
 */
public class RendererMap {

    private final ArrayList<Entry> entries;
    private final Comparator<Entry> comparator;

    public RendererMap() {
        this.entries = new ArrayList<>();
        this.comparator = (x, y)
                -> Float.compare(
                        x.getValue().getRenderPriority(),
                        y.getValue().getRenderPriority());
    }
    
    public RendererMap(Comparator<Entry> comparator){
        this.entries = new ArrayList<>();
        this.comparator = comparator;
    }
    
    public Stream<Renderer> getRenderers(){
        return this.entries.stream().map(x -> x.getValue());
    }

    public <S extends ShaderProgram, M extends Material<S>> void put(Renderer<S, M> renderer) {
        Class<S> shaderClass = renderer.getShaderClass();
        Entry<S, M> entry = new Entry<>(shaderClass, renderer);
        this.entries.add(entry);
        this.entries.sort(this.comparator);
    }

    public <S extends ShaderProgram, M extends Material<S>> Renderer<S, M> get(Class<S> shaderClass) {
        @SuppressWarnings("unchecked")
        Entry<S, M> entry = this.entries.stream()
                .filter(x -> x.getKey().equals(shaderClass))
                .findFirst()
                .get();

        return entry.getValue();
    }

    public boolean containsRendererFor(Class<? extends ShaderProgram> shaderClass) {
        return this.entries.stream()
                .anyMatch(x -> x.getKey().equals(shaderClass));
    }

    public <S extends ShaderProgram, M extends Material<S>> boolean containsRendererFor(RenderComponent<S, M> renderComponent) {
        return this.containsRendererFor(renderComponent.getMaterial().getShaderClass());
    }

    public <S extends ShaderProgram, M extends Material<S>> Renderer<S, M> getRendererFor(RenderComponent<S, M> renderComponent) {
        return this.get(renderComponent.getMaterial().getShaderClass());
    }
    
    public void destroy(){
        this.entries.forEach(x -> x.getValue().destroy());
        this.entries.clear();
    }

}
