/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.components.base.ComponentBase;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action2;
import rx.functions.Func1;

/**
 *
 * @author Blackened
 * @param <T>
 */
public class ActionListenerComponent<T extends Object> extends ComponentBase {

    private Func1<T, Boolean> filter;
    private Action2<T, Entity> action;
    private Observable<T> actionSubject;

    private Subscription subscription;

    public ActionListenerComponent(Observable actionSubject, Func1<T, Boolean> filter, Action2<T, Entity> action) {
        this.filter = filter;
        this.action = action;
        this.actionSubject = this.castObservable(actionSubject);
    }

    @SuppressWarnings("unchecked")
    private Observable<T> castObservable(Observable obs) {
        return (Observable<T>) obs;
    }

    private void handleInput(T input) {
        this.action.call(input, this.getParent());
    }

    @Override
    public void activate() {
        this.subscription = actionSubject
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

}
