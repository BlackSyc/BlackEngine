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
package blackengine.toolbox.guard;

import blackengine.toolbox.guard.exceptions.InvalidArgumentException;
import blackengine.toolbox.guard.exceptions.NullArgumentException;
import java.util.function.Predicate;

/**
 *
 * @author Blackened
 */
public class CheckedGuard {

    public void notNull(Object obj) throws NullArgumentException {
        this.notNull(obj, "Argument can not be null.");
    }

    public void notNull(Object obj, String message) throws NullArgumentException {
        if (obj == null) {
            throw new NullArgumentException(message);
        }
    }

    public <T> void validate(T obj, Predicate<T> predicate) throws InvalidArgumentException {
        this.validate(obj, predicate, "Argument " + obj.toString() + " is invalid.");
    }

    public <T> void validate(T obj, Predicate<T> predicate, String message) throws InvalidArgumentException {
        if (!predicate.test(obj)) {
            throw new InvalidArgumentException(message);
        }
    }

    public void validate(boolean value) throws InvalidArgumentException {
        this.validate(value, "Argument is invalid.");
    }

    public void validate(boolean value, String message) throws InvalidArgumentException {
        if (!value) {
            throw new InvalidArgumentException(message);
        }
    }

}
