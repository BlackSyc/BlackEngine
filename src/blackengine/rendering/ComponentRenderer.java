/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;

import blackengine.gameLogic.components.base.RenderComponentBase;

/**
 *
 * @author Blackened
 * @param <T>
 */
public abstract class ComponentRenderer<T extends RenderComponentBase> extends POVRendererBase {
    
    /**
     * 
     * @param vertexFile
     * @param fragmentFile 
     */
    public ComponentRenderer(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }
    
    /**
     * 
     * @param renderComponent 
     */
    public abstract void addRenderTarget(T renderComponent);
    
    /**
     * 
     * @param renderComponent 
     */
    public abstract void removeRenderTarget(T renderComponent);
    
    /**
     * 
     * @param renderComponent
     * @return 
     */
    public abstract boolean containsRenderTarget(T renderComponent);
    
    
}
