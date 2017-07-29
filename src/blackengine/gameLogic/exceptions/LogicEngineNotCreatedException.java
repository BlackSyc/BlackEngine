/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.exceptions;

/**
 *
 * @author Blackened
 */
public class LogicEngineNotCreatedException extends RuntimeException {

    public LogicEngineNotCreatedException() {
        super("The LogicEngine was not created. Create it from a game manager before performing any game logic operations.");
    }

}
