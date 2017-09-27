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
package blackengine.gameLogic.movement;

import blackengine.toolbox.math.ImmutableVector3;

/**
 *
 * @author Blackened
 */
public enum MoveDirection {

    FORWARD {

        @Override
        public ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation) {
            float dx = (float) (amount * Math.sin(Math.toRadians(currentRotation.getY())));
            float dz = (float) (amount * Math.cos(Math.toRadians(currentRotation.getY())));
            return new ImmutableVector3(-dx, 0, -dz);
        }
    },
    BACKWARD {
        @Override
        public ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation) {
            float dx = (float) (amount * Math.sin(Math.toRadians(currentRotation.getY())));
            float dz = (float) (amount * Math.cos(Math.toRadians(currentRotation.getY())));
            return new ImmutableVector3(dx, 0, dz);
        }
    },
    LEFT {
        @Override
        public ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation) {
            float dx = (float) (amount * Math.sin(Math.toRadians(currentRotation.getY()) - 0.5 * Math.PI));
            float dz = (float) (amount * Math.cos(Math.toRadians(currentRotation.getY()) - 0.5 * Math.PI));
            return new ImmutableVector3(dx, 0, dz);
        }
    },
    RIGHT {
        @Override
        public ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation) {
            float dx = (float) (amount * Math.sin(Math.toRadians(currentRotation.getY()) - 0.5 * Math.PI));
            float dz = (float) (amount * Math.cos(Math.toRadians(currentRotation.getY()) - 0.5 * Math.PI));
            return new ImmutableVector3(-dx, 0, -dz);
        }
    },
    UP {
        @Override
        public ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation) {
            return new ImmutableVector3(0, amount, 0);
        }
    },
    DOWN {
        @Override
        public ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation) {
            return new ImmutableVector3(0, -amount, 0);
        }
    };

    public abstract ImmutableVector3 calculateTranslation(float amount, ImmutableVector3 currentRotation);

}
