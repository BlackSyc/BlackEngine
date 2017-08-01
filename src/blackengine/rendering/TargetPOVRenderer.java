/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;


/**
 *
 * @author Blackened
 * @param <T>
 */
public abstract class TargetPOVRenderer<T> extends POVRendererBase {
    
    /**
     * 
     * @param vertexFile
     * @param fragmentFile 
     */
    public TargetPOVRenderer(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }
    
    /**
     * 
     * @param renderTarget
     */
    public abstract void addRenderTarget(T renderTarget);
    
    /**
     * 
     * @param renderTarget
     */
    public abstract void removeRenderTarget(T renderTarget);
    
    /**
     * 
     * @param renderTarget
     * @return 
     */
    public abstract boolean containsRenderTarget(T renderTarget);
    
    
}
