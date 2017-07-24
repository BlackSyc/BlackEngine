/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;

import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 */
public abstract class POVRendererBase extends RendererBase{

    public POVRendererBase(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }
    
    /**
     * Render the targets.
     *
     * @param viewMatrix The view matrix of the camera to calculate the position
     * of 3d elements on screen.
     */
    public abstract void render(Matrix4f viewMatrix);
    
}
