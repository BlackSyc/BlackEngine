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

import blackengine.userInput.exceptions.InputEngineNotCreatedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import rx.Observable;

/**
 *
 * @author Blackened
 * @param <T>
 */
public class InputEngine<T extends Object> {

    //<editor-fold defaultstate="collapsed" desc="Instance">
    private static InputEngine<? extends Object> INSTANCE;

    public static InputEngine<? extends Object> getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new InputEngineNotCreatedException();
    }

    private InputEngine(Observable<T> actionObservable, Observable<MouseEvent> mouseObservable) {
        this.actionObservable = actionObservable;
        this.mouseObservable = mouseObservable;
    }

    protected static void create(Observable<? extends Object> actionObservable, Observable<MouseEvent> mouseObservable) {
        INSTANCE = new InputEngine<>(actionObservable, mouseObservable);
        try {
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(InputEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void destroy() {
        INSTANCE = null;
        Keyboard.destroy();
        Mouse.destroy();
    }
    //</editor-fold>
    
    private Observable<T> actionObservable;
    
    private Observable<MouseEvent> mouseObservable;
    
    private float mouseSensitivity = 1;

    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    public Observable<T> getActionObservable() {
        return actionObservable;
    }

    public Observable<MouseEvent> getMouseObservable() {
        return mouseObservable;
    }

}
