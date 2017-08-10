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

    public void destroyEngine() {
        if (InputEngine.getInstance() != null) {
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
                } else {
                    this.mouseSubject.onNext(MOUSEUP.at(Mouse.getX(), Mouse.getY()).withButton(Mouse.getEventButton()).withDelta(dx, dy));
                }
            }
        }
        this.mouseSubject.onNext(HOVER.at(Mouse.getX(), Mouse.getY()).withDelta(dx, dy));

    }

}
