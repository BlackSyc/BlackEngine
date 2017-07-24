/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.base;

import blackengine.gameLogic.Game;
import org.lwjgl.opengl.Display;
import blackengine.rendering.DisplayManager;
import blackengine.userInput.InputManager;

/**
 *
 * @author Blackened
 */
public abstract class ApplicationManager {

    private boolean isRunning;

    private InputManager inputManager;

    private Game game;

    private DisplayManager displayManager;

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public void setDisplayManager(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    

    public ApplicationManager() {
        this.displayManager = new DisplayManager(60);
    }

    public void startApplication() {
        this.isRunning = true;
        setUp();

        while (!Display.isCloseRequested() && isRunning) {

            if (this.inputManager != null) {
                this.inputManager.handleInput();
            }

            if (this.game != null) {
                this.game.updateActiveScene();
                this.game.updateActiveUI();
            }

            this.displayManager.render();

        }

        cleanUp();
        this.isRunning = false;
    }

    public abstract void setUp();

    public abstract void cleanUp();

}
