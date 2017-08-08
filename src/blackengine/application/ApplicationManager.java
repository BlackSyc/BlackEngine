/* 
 * The MIT License
 *
 * Copyright 2017 Blackened.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package blackengine.application;

import blackengine.gameLogic.GameManager;
import blackengine.gameLogic.LogicEngine;
import org.lwjgl.opengl.Display;
import blackengine.rendering.DisplayManager;
import blackengine.userInput.InputManager;

/**
 * An implementation of this abstract class will manage the outside of the
 * entire application.
 *
 * @author Blackened
 */
public abstract class ApplicationManager {

    /**
     * Flags whether the game loop should be running.
     */
    private boolean isRunning;

    /**
     * The game manager that is responsible for handling all game state updates.
     */
    private GameManager gameManager;

    /**
     * The display manager that is responsible for updating the display, as well
     * as driving the master renderer.
     */
    private DisplayManager displayManager;

    /**
     * The input manager that is responsible for handling all inputs and making
     * sure that all elements of the application that are subscribed to an input
     * event will be notified.
     */
    private InputManager inputManager;

    /**
     * Getter for the display manager.
     *
     * @return The display manager that is responsible for updating the display,
     * as well as driving the master renderer.
     */
    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    /**
     * Setter for the display manager.
     *
     * @param displayManager An implementation of
     * {@link blackengine.rendering.DisplayManager DisplayManager} that will be
     * responsible for updating the display, as well as driving the master
     * renderer.
     */
    public void setDisplayManager(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }

    /**
     * Getter for the game manager.
     *
     * @return The game manager that is responsible for handling all game state
     * updates.
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Setter for the game manager.
     *
     * @param gameManager An implementation of
     * {@link blackengine.gameLogic.GameManager GameManager} that will be
     * responsible for handling all game state updates.
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Getter for the input manager.
     *
     * @return The input manager that is responsible for handling all inputs and
     * making sure that all elements of the application that are subscribed to
     * an input event will be notified.
     */
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * Setter for the input manager.
     *
     * @param inputManager An implementation of
     * {@link blackengine.userInput.InputManager InputManager} that will be
     * responsible for handling all inputs and making sure that all elements of
     * the application that are subscribed to an input event will be notified.
     */
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * Constructor for creating a new instance of ApplicationManager.
     */
    public ApplicationManager() {
        this.displayManager = new DisplayManager(60);
    }

    /**
     * Starts a new application by calling an implementation of the abstract
     * {@link #setUp() setUp} method, followed by the game loop and finally an
     * implementation of the abstract {@link #cleanUp() cleanUp} method.
     *
     * <p>
     * The game loop itself can be stopped by calling the {@link #quit() quit}
     * method or by requesting to close on the display itself. In both cases,
     * the current iteration will complete, and will be followed by calling an
     * implementation of the abstract {@link #cleanUp() cleanUp} method.</p>
     *
     * The loop consists of three main parts, in order:
     * <ul>
     * <li>Have the InputManager handle all keyboard and mouse input.</li>
     * <li>Have the GameManager handle state updates to the scene and/or user
     * interface.</li>
     * <li>Have the DisplayManager handle all rendering and updating of the
     * display.</li>
     * </ul>
     */
    public void startApplication() {
        if (!this.isRunning) {
            this.isRunning = true;
            setUp();

            while (!Display.isCloseRequested() && isRunning) {

                if (this.inputManager != null) {
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
    }

    /**
     * Will break the game loop once this iteration has finished.
     */
    public void quit() {
        this.isRunning = false;
    }

    /**
     * Will contain all logic that will have to be run <b>before</b> the game
     * loop is started.
     */
    public abstract void setUp();

    /**
     * Will contain all logic that will have to be run <b>after</b> the game
     * loop has ended.
     */
    public abstract void cleanUp();

}
