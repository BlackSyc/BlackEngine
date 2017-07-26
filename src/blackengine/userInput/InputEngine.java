/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput;

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
        return INSTANCE;
    }

    private InputEngine(Observable<T> actionObservable) {
        this.actionObservable = actionObservable;
    }
    
    protected static void create(Observable<? extends Object> actionObservable){
        INSTANCE = new InputEngine<>(actionObservable);
    }
    
    //</editor-fold>
    
    

    private Observable<T> actionObservable;

    public Observable<T> getActionObservable() {
        return actionObservable;
    }
    
    
}
