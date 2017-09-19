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
import io.reactivex.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Engine singleton for input management. Can be used to retrieve the mouse
 * event observable or action observable.
 *
 * @author Blackened
 * @param <T> The type of action returned from the key mapper.
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

    /**
     * Getter for the mouse sensitivity.
     *
     * @return A float representing the mouse sensitivity.
     */
    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    /**
     * Setter for the mouse sensitivity.
     *
     * @param mouseSensitivity A float representing the mouse sensitivity.
     */
    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    /**
     * Getter for the action observable. This observable can be subscribed to by
     * components or other classes to listen to action requests.
     *
     * @return An instance of Observable&lt;T&gt;.
     */
    public Observable<T> getActionObservable() {
        return actionObservable;
    }

    /**
     * Getter for the mouse event observable. This observable can be subscribed
     * to by components or other classes to listen to mouse events.
     *
     * @return An instance of
     * Observable&lt;{@link blackengine.userInput.MouseEvent MouseEvent}&gt;.
     */
    public Observable<MouseEvent> getMouseObservable() {
        return mouseObservable;
    }

    private InputEngine(Observable<T> actionObservable, Observable<MouseEvent> mouseObservable) {
        this.actionObservable = actionObservable;
        this.mouseObservable = mouseObservable;
    }

    /**
     * Makes the cursor invisible and keeps it in its position. Mouse events act
     * as though the mouse was not grabbed.
     *
     * @param value True if the cursor should be hidden, false otherwise.
     */
    public void setMouseGrabbed(boolean value) {
        Mouse.setGrabbed(value);
    }

    /**
     * Retrieves whether the cursor is hidden or not.
     *
     * @return True if the cursor is hidden, false otherwise.
     */
    public boolean isMouseGrabbed() {
        return Mouse.isGrabbed();
    }

}
