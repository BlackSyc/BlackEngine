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
        this.dx = 0;
        this.dy = 0;
        this.button = -1;
    }
    
    MouseEvent(int button){
        this.x = 0;
        this.y = 0;
        this.dx = 0;
        this.dy = 0;
        this.button = button;
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
    
    public MouseEvent withDelta(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
        return this;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getButton() {
        return button;
    }

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int button;

}
