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
package blackengine.toolbox.math;

import java.io.Serializable;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Blackened
 */
public class ImmutableVector2 implements Serializable{

    private float x;

    private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ImmutableVector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public ImmutableVector2(Vector2f vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public ImmutableVector2() {
    }

    public Vector2f mutable() {
        return new Vector2f(this.x, this.y);
    }

    @Override
    public ImmutableVector2 clone() {
        return new ImmutableVector2(this.x, this.y);
    }

    public float length() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public float distanceTo(ImmutableVector2 vector) {
        float xDistance = Math.abs(this.x - vector.getX());
        float yDistance = Math.abs(this.y - vector.getY());
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        return distance;
    }

    //<editor-fold defaultstate="collapsed" desc="Addition">
    public ImmutableVector2 add(ImmutableVector2 addition) {
        return new ImmutableVector2(this.x + addition.getX(),
                this.y + addition.getY());
    }

    public ImmutableVector2 add(float x, float y, float z) {
        return new ImmutableVector2(this.x + x,
                this.y + y);
    }

    public ImmutableVector2 add(float addition) {
        return new ImmutableVector2(this.x + addition,
                this.y + addition);
    }

    public ImmutableVector2 addToX(float addition) {
        return new ImmutableVector2(this.x + addition,
                this.y);
    }

    public ImmutableVector2 addToY(float addition) {
        return new ImmutableVector2(this.x,
                this.y + addition);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Subtraction">
    public ImmutableVector2 subtract(ImmutableVector2 subtraction) {
        return new ImmutableVector2(this.x - subtraction.getX(),
                this.y - subtraction.getY());
    }

    public ImmutableVector2 subtract(float subtraction) {
        return new ImmutableVector2(this.x - subtraction,
                this.y - subtraction);
    }

    public ImmutableVector2 subtractFromX(float subtraction) {
        return new ImmutableVector2(this.x - subtraction,
                this.y);
    }

    public ImmutableVector2 subtractFromY(float subtraction) {
        return new ImmutableVector2(this.x,
                this.y - subtraction);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Multiplication">
    public ImmutableVector2 multiplyBy(ImmutableVector2 multiplication) {
        return new ImmutableVector2(this.x * multiplication.getX(),
                this.y * multiplication.getY());
    }

    public ImmutableVector2 multiplyBy(float multiplication) {
        return new ImmutableVector2(this.x * multiplication,
                this.y * multiplication);
    }

    public ImmutableVector2 multiplyXBy(float multiplication) {
        return new ImmutableVector2(this.x * multiplication,
                this.y);
    }

    public ImmutableVector2 multiplyYBy(float multiplication) {
        return new ImmutableVector2(this.x,
                this.y * multiplication);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Division">
    public ImmutableVector2 divideBy(ImmutableVector2 division) {
        return new ImmutableVector2(this.x / division.getX(),
                this.y / division.getY());
    }

    public ImmutableVector2 divideBy(float division) {
        return new ImmutableVector2(this.x / division,
                this.y / division);
    }

    public ImmutableVector2 divideXBy(float division) {
        return new ImmutableVector2(this.x / division,
                this.y);
    }

    public ImmutableVector2 divideYBy(float division) {
        return new ImmutableVector2(this.x,
                this.y / division);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Negation">
    public ImmutableVector2 negate() {
        return new ImmutableVector2(-this.x, -this.y);
    }

    public ImmutableVector2 negateX() {
        return new ImmutableVector2(-this.x, this.y);
    }

    public ImmutableVector2 negateY() {
        return new ImmutableVector2(this.x, -this.y);
    }
    //</editor-fold>

    public boolean equals(ImmutableVector2 other, float tolerance) {
        return Math.abs(this.getX() - other.getX()) < tolerance
                && Math.abs(this.getY() - other.getY()) < tolerance;
    }

}
