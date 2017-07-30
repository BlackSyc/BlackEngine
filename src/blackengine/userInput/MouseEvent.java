/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput;

/**
 *
 * @author Blackened
 */
public enum MouseEvent {

    /**
     *
     */
    HOVER,
    MOUSEDOWN,
    MOUSEUP;

    MouseEvent() {
        this.x = 0;
        this.y = 0;
        this.button = -1;
    }

    public MouseEvent at(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public MouseEvent withButton(int button) {
        this.button = button;
        return this;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getButton() {
        return button;
    }

    private int x;
    private int y;
    private int button;

}
