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

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BooleanSupplier;

/**
 * An instance of KeyActionMapper can be used to tie actions to certain keyboard
 * states or keyboard events.
 *
 * @author Blackened
 */
public class KeyActionMapper<T> {

    /**
     * An instance of HashMap that maps a boolean supplier that checks the state
     * to any defined action.
     */
    private HashMap<BooleanSupplier, T> keyActionMap;

    /**
     * An instance of HashMap that maps a boolean supplier that checks for
     * keyboard events to any defined action.
     */
    private HashMap<BooleanSupplier, T> eventActionMap;

    /**
     * Default constructor for creating a new instance of KeyActionMapper.
     *
     * @param keyActionMap An instance of HashMap that maps a boolean supplier
     * that checks the state to any defined action.
     * @param eventActionMap An instance of HashMap that maps a boolean supplier
     * that checks for keyboard events to any defined action.
     */
    public KeyActionMapper(HashMap<BooleanSupplier, T> keyActionMap, HashMap<BooleanSupplier, T> eventActionMap) {
        this.keyActionMap = keyActionMap;
        this.eventActionMap = eventActionMap;
    }

    /**
     * Returns an iterator of all boolean suppliers that check for state.
     *
     * @return An instance of Iterator&lt;BooleanSupplier&gt;.
     */
    public Iterator<BooleanSupplier> getMappedKeyIterator() {
        return this.keyActionMap.keySet().iterator();
    }

    /**
     * Returns an iterator of all boolean suppliers that check for keyboard
     * events.
     *
     * @return An instance of Iterator&lt;BooleanSupplier&gt;.
     */
    public Iterator<BooleanSupplier> getMappedEventIterator() {
        return this.eventActionMap.keySet().iterator();
    }

    /**
     * Getter for all actions that have matching boolean suppliers for checking
     * state in this instance of KeyAcctionMapper.
     *
     * @return An instance of Iterator&lt;T&gt;.
     */
    public Iterator<T> getActionIterator() {
        return this.keyActionMap.values().iterator();
    }

    /**
     * Getter for all actions that have matching boolean suppliers for checking
     * keyboard events in this instance of KeyAcctionMapper.
     *
     * @return An instance of Iterator&lt;T&gt;.
     */
    public Iterator<T> getEventActionIterator() {
        return this.eventActionMap.values().iterator();
    }

    /**
     * Getter for a specific action that matches the specified boolean supplier
     * that checks for state.
     *
     * @param key The boolean supplier that will be used to look up its action.
     * @return An instance of T, representing the action that will be sent out
     * upon satisfaction of the boolean supplier.
     */
    public T getAction(BooleanSupplier key) {
        return this.keyActionMap.get(key);
    }

    /**
     * Getter for a specific action that matches the specified boolean supplier
     * that checks for keyboard events.
     *
     * @param key The boolean supplier that will be used to look up its action.
     * @return An instance of T, representing the action that will be sent out
     * upon satisfaction of the boolean supplier.
     */
    public T getEventAction(BooleanSupplier key) {
        return this.eventActionMap.get(key);
    }

}
