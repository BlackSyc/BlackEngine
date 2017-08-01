/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.userInput.MouseEvent;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action2;
import rx.functions.Func1;

/**
 *
 * @author Blackened
 */
public class MouseListenerComponent extends ComponentBase {

    private Observable<MouseEvent> mouseObservable;

    private Subscription subscription;

    private Func1<MouseEvent, Boolean> filter;
    private Action2<MouseEvent, Entity> action;

    public MouseListenerComponent(Observable<MouseEvent> mouseObservable, Func1<MouseEvent, Boolean> filter, Action2<MouseEvent, Entity> action) {
        this.mouseObservable = mouseObservable;
        this.filter = filter;
        this.action = action;
    }

    

    @Override
    public void activate() {
        this.subscription = mouseObservable
                .filter(filter)
                .subscribe(x -> this.handleInput(x));
        super.activate();
    }

    @Override
    public void deactivate() {
        this.subscription.unsubscribe();
        super.deactivate();
    }

    @Override
    public void update() {
    }
    
    private void handleInput(MouseEvent input) {
        this.action.call(input, this.getParent());
    }
}
