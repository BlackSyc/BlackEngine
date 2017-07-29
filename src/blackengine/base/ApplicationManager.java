/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.base;

import blackengine.gameLogic.GameManager;
import blackengine.gameLogic.LogicEngine;
import org.lwjgl.opengl.Display;
import blackengine.rendering.DisplayManager;
import blackengine.userInput.InputManager;

/**
 *
 * @author Blackened
 */
public abstract class ApplicationManager {

    private boolean isRunning;

    private GameManager gameManager;

    private DisplayManager displayManager;

    private InputManager inputManager;

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public void setDisplayManager(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public ApplicationManager() {
        this.displayManager = new DisplayManager(60);
    }

    public void startApplication() {
        this.isRunning = true;
        setUp();

        while (!Display.isCloseRequested() && isRunning) {
            
            if(this.inputManager != null){
                this.inputManager.handleInput();
            }

            if (this.gameManager != null) {
                this.gameManager.updateActiveScene();
                this.gameManager.updateActiveUI();
            }

            this.displayManager.render();
            LogicEngine.getInstance().getTimer().registerFrame();

        }

        cleanUp();
        this.isRunning = false;
    }
    
    public void quit(){
        this.isRunning = false;
    }

    public abstract void setUp();

    public abstract void cleanUp();

}
