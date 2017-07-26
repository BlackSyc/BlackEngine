/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput;

import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 *
 * @author Blackened
 */
public class InputManager<T> {

    private PublishSubject<T> actionSubject;

    private KeyActionMapper<T> keyActionMapper;

    public Observable<T> getActionSubject() {
        return actionSubject;
    }

    public KeyActionMapper<T> getKeyActionMapper() {
        return keyActionMapper;
    }

    public InputManager(KeyActionMapper<T> keyActionMapper) {
        this.keyActionMapper = keyActionMapper;
        this.actionSubject = PublishSubject.create();
    }

    public void createEngine() {
        InputEngine.create(this.actionSubject);
    }

    public void handleInput() {
        this.handleSingleKeys();
    }

    private void handleSingleKeys() {
        Iterator<Integer> mappedKeyIterator = this.keyActionMapper.getMappedKeyIterator();

        while (mappedKeyIterator.hasNext()) {
            Integer key = mappedKeyIterator.next();
            if (Keyboard.isKeyDown(key)) {
                this.actionSubject.onNext(this.keyActionMapper.getAction(key));
            }
        }
    }

}
