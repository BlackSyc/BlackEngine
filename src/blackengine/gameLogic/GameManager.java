/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

/**
 *
 * @author Blackened
 */
public class GameManager {

    private GameElement activeScene;

    private GameElement activeUserInterface;

    public GameElement getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(GameElement activeScene) {
        this.activeScene = activeScene;
    }

    public GameElement getActiveUserInterface() {
        return activeUserInterface;
    }

    public void setActiveUserInterface(GameElement activeUserInterface) {
        this.activeUserInterface = activeUserInterface;
    }

    public void createEngine() {
        LogicEngine.create();
    }

    public void destroyGameElements() {
        if (this.activeScene != null) {
            this.activeScene.destroy();
        }
        if (this.activeUserInterface != null) {
            this.activeUserInterface.destroy();
        }
        this.activeScene = null;
        this.activeUserInterface = null;
    }

    public GameManager() {
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
