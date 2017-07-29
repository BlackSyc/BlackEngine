/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput.exceptions;

/**
 *
 * @author Blackened
 */
public class InputEngineNotCreatedException extends RuntimeException {

    public InputEngineNotCreatedException() {
        super("The InputEngine was not created. Create it from an input manager before performing any input operations.");
    }

}
