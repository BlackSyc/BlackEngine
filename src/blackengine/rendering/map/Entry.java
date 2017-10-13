/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.map;

import blackengine.rendering.renderers.Material;
import blackengine.rendering.renderers.Renderer;
import blackengine.rendering.renderers.ShaderProgram;


/**
 *
 * @author Blackened
 * @param <S>
 * @param <M>
 */
public class Entry<S extends ShaderProgram, M extends Material<S>> {
    
    private final Class<S> key;
    
    private final Renderer<S, M> value;

    public Class<S> getKey() {
        return key;
    }

    public Renderer<S, M> getValue() {
        return value;
    }

    public Entry(Class<S> key, Renderer<S, M> value) {
        this.key = key;
        this.value = value;
    }
    
    
    
}
