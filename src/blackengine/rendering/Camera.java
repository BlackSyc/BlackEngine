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
public interface Camera {
    
    public Matrix4f getViewMatrix();
    
}
