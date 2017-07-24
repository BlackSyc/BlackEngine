/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;

/**
 *
 * @author Blackened
 */
public abstract class FlatRendererBase extends RendererBase {

    public FlatRendererBase(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }

    /**
     * Render the targets.
     *
     */
    public abstract void render();

}
