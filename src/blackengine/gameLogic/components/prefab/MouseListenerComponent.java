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

    protected void setFilter(Func1<MouseEvent, Boolean> filter) {
        this.filter = filter;
    }

    protected void setAction(Action2<MouseEvent, Entity> action) {
        this.action = action;
    }

    public MouseListenerComponent(Observable<MouseEvent> mouseObservable, Func1<MouseEvent, Boolean> filter, Action2<MouseEvent, Entity> action) {
        this.mouseObservable = mouseObservable;
        this.filter = filter;
        this.action = action;
    }
    
    protected MouseListenerComponent(Observable<MouseEvent> mouseObservable){
        this.mouseObservable = mouseObservable;
    }

    

    @Override
    public void onActivate() {
        this.subscription = mouseObservable
                .filter(filter)
                .subscribe(x -> this.handleInput(x));
    }

    @Override
    public void onDeactivate() {
        this.subscription.unsubscribe();
    }

    @Override
    public void update() {
    }
    
    private void handleInput(MouseEvent input) {
        this.action.call(input, this.getParent());
    }
}
