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
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * An instance of InputManager makes sure to handle all input specified in the
 * provided key mapper. Components and other parts of the game can subscribe to
 * either the mouse observable or the action observable.
 *
 * @author Blackened
 */
public class InputManager<T> {

    /**
     * The action subject sends out actions of type T to all subscribers when
     * their requirements (set in the key mapper) are met.
     */
    private PublishSubject<T> actionSubject;

    /**
     * The mouse subject sends out mouse events to all subscribers.
     */
    private PublishSubject<MouseEvent> mouseSubject;

    /**
     * The key mapper defines which keys or key events to listen to in order to
     * send out actions.
     */
    private final KeyActionMapper<T> keyActionMapper;

    /**
     * Getter for the instance of Observable&lt;T&gt; that can be subscribed to
     * in order to receive actions.
     *
     * @return An instance of Observable&lt;T&gt;.
     */
    public Observable<T> getActionObservable() {
        return actionSubject;
    }

    /**
     * Getter for the instance of
     * Observable&lt;{@link blackengine.userInput.MouseEvent MouseEvent}&gt;
     * that can be subscribed to in order to receive the events.
     *
     * @return An instance of
     * Observable&lt;{@link blackengine.userInput.MouseEvent MouseEvent}&gt;.
     */
    public Observable<MouseEvent> getMouseObservable() {
        return mouseSubject;
    }

    /**
     * Default constructor for creating a new instance of InputManager.
     *
     * @param keyActionMapper An instance of
     * {@link blackengine.userInput.KeyActionMapper KeyActionMapper} that will
     * be used to iterate over the keyboards state and events to send out
     * actions through the action observable.
     */
    public InputManager(KeyActionMapper<T> keyActionMapper) {
        this.keyActionMapper = keyActionMapper;
        this.actionSubject = PublishSubject.create();
        this.mouseSubject = PublishSubject.create();
    }

    /**
     * Creates the {@link blackengine.userInput.InputEngine InputEngine}, which
     * can be used to retrieve either observable at any point. Best practice is
     * to call this method within the
     * {@link blackengine.application.ApplicationManager#setUp() setUp()} method
     * inside an implementation of
     * {@link blackengine.application.ApplicationManager ApplicationManager}.
     */
    public void createEngine() {
        InputEngine.create(this.actionSubject, this.mouseSubject);
    }

    /**
     * Destroys the {@link blackengine.userInput.InputEngine InputEngine}
     */
    public void destroyEngine() {
        if (InputEngine.getInstance() != null) {
            InputEngine.getInstance().destroy();
        }
    }

    /**
     * Calls onCompleted() on the subjects and sets them to null.
     */
    public void destroySubjects() {
        this.actionSubject.onCompleted();
        this.mouseSubject.onCompleted();
        this.actionSubject = null;
        this.mouseSubject = null;
    }

    /**
     * Handles all input for the application. This should be called from the
     * game loop (already defined in
     * {@link blackengine.application.ApplicationManager ApplicationManager}.
     */
    public void handleInput() {
        this.handleKeyEvents();
        this.handleKeys();
        this.handleMouseInput();
    }

    /**
     * Handles all events retrieved from the keyboard by sending out the
     * appropriate actions defined in the key mapper.
     */
    private void handleKeyEvents() {
        while (Keyboard.next()) {
            Iterator<BooleanSupplier> mappedEventIterator = this.keyActionMapper.getMappedEventIterator();
            while (mappedEventIterator.hasNext()) {
                BooleanSupplier booleanSupplier = mappedEventIterator.next();
                if (booleanSupplier.getAsBoolean()) {
                    this.actionSubject.onNext(this.keyActionMapper.getEventAction(booleanSupplier));
                }
            }
        }
    }

    /**
     * Handles all key states from the keyboard by sending out the appropriate
     * actions defined in the key mapper.
     */
    private void handleKeys() {
        Iterator<BooleanSupplier> mappedKeyIterator = this.keyActionMapper.getMappedKeyIterator();

        while (mappedKeyIterator.hasNext()) {
            BooleanSupplier booleanSupplier = mappedKeyIterator.next();
            if (booleanSupplier.getAsBoolean()) {
                this.actionSubject.onNext(this.keyActionMapper.getAction(booleanSupplier));
            }
        }
    }

    /**
     * Handles all events from the mouse by sending out the appropriate events
     * that are predefined in BlackEngine.
     */
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
