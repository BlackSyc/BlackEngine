/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering.exceptions;

/**
 *
 * @author Blackened
 */
public class RenderEngineNotCreatedException extends RuntimeException {

    public RenderEngineNotCreatedException() {
        super("The RenderEngine was not created. Create it from a display manager before performing any render operations.");
    }

}
