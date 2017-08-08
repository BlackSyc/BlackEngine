/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput;

import static blackengine.userInput.MouseEvent.*;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import org.lwjgl.input.Mouse;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 *
 * @author Blackened
 */
public class InputManager<T> {

    private PublishSubject<T> actionSubject;

    private PublishSubject<MouseEvent> mouseSubject;

    private final KeyActionMapper<T> keyActionMapper;

    public Observable<T> getActionObservable() {
        return actionSubject;
    }

    public KeyActionMapper<T> getKeyActionMapper() {
        return keyActionMapper;
    }

    public Observable<MouseEvent> getMouseObservable() {
        return mouseSubject;
    }

    public InputManager(KeyActionMapper<T> keyActionMapper) {
        this.keyActionMapper = keyActionMapper;
        this.actionSubject = PublishSubject.create();
        this.mouseSubject = PublishSubject.create();
    }

    public void createEngine() {
        InputEngine.create(this.actionSubject, this.mouseSubject);
    }
    
    public void destroyEngine(){
        if(InputEngine.getInstance() != null){
            InputEngine.getInstance().destroy();
        }
    }

    public void destroySubjects() {
        this.actionSubject = null;
        this.mouseSubject = null;
    }

    public void handleInput() {
        this.handleSingleKeys();
        this.handleMouseInput();
    }

    private void handleSingleKeys() {
        Iterator<BooleanSupplier> mappedKeyIterator = this.keyActionMapper.getMappedKeyIterator();

        while (mappedKeyIterator.hasNext()) {
            BooleanSupplier booleanSupplier = mappedKeyIterator.next();
            if (booleanSupplier.getAsBoolean()) {
                this.actionSubject.onNext(this.keyActionMapper.getAction(booleanSupplier));
            }
        }
    }

    private void handleMouseInput() {
        int dx = Mouse.getDX();
        int dy = Mouse.getDY();
        while (Mouse.next()) {
            if (Mouse.getEventButton() != -1) {
                if (Mouse.getEventButtonState()) {
                    this.mouseSubject.onNext(MOUSEDOWN.at(Mouse.getX(), Mouse.getY()).withButton(Mouse.getEventButton()).withDelta(dx, dy));
                }
                else{
                    this.mouseSubject.onNext(MOUSEUP.at(Mouse.getX(), Mouse.getY()).withButton(Mouse.getEventButton()).withDelta(dx, dy));
                }
            }
        }
        if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)){
            if(Mouse.isButtonDown(0))
                this.mouseSubject.onNext(DRAG_LMB.at(Mouse.getX(), Mouse.getY()).withDelta(dx, dy));
            if(Mouse.isButtonDown(1))
                this.mouseSubject.onNext(DRAG_RMB.at(Mouse.getX(), Mouse.getY()).withDelta(dx, dy));
        }
        else{
            this.mouseSubject.onNext(HOVER.at(Mouse.getX(), Mouse.getY()).withDelta(dx, dy));
        }
        
    }

}
