/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.components.base.ComponentBase;
import java.util.List;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func1;
import blackengine.userInput.InputAction;

/**
 *
 * @author Blackened
 */
public class ActionListenerComponent extends ComponentBase {

    private final Observable<InputAction> inputActionSubject;

    private final Action2<Entity, InputAction> action;

    private final Func1<InputAction, Boolean> inputActionFilterPredicate;

    private final List<InputAction> inputActionFilter;

    public ActionListenerComponent(
            Observable<InputAction> inputActionSubject,
            List<InputAction> inputActionFilter,
            Action2<Entity, InputAction> action) {

        this.inputActionSubject = inputActionSubject;
        this.inputActionFilter = inputActionFilter;
        this.inputActionFilterPredicate = x -> this.inputActionFilter.contains(x);
        this.action = action;
        this.inputActionSubject.filter(this.inputActionFilterPredicate).subscribe(x -> this.handleInput(x));
    }

    private void handleInput(InputAction inputAction) {
        this.action.call(super.getParent(), inputAction);
    }

    @Override
    public void update() {
    }

}
