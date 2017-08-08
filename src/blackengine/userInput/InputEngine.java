/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
