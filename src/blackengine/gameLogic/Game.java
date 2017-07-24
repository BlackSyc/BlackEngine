/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

import blackengine.uiLogic.UserInterface;

/**
 *
 * @author Blackened
 */
public class Game {

    private Scene activeScene;

    private UserInterface activeUserInterface;

    public Scene getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Scene activeScene) {
        this.activeScene = activeScene;
    }

    public Game() {
    }

    public void updateActiveScene() {
        if (this.activeScene != null) {
            this.activeScene.update();
        }
    }

    public void updateActiveUI() {
        if (this.activeUserInterface != null) {
            this.activeUserInterface.update();
        }
    }

}
