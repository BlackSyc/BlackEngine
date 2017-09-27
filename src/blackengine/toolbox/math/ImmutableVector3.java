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

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Blackened
 */
public class ImmutableVector3 {

    private final float x;

    private final float y;

    private final float z;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public ImmutableVector3(Vector3f vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    public ImmutableVector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public ImmutableVector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f mutable() {
        return new Vector3f(this.x, this.y, this.z);
    }

    @Override
    public ImmutableVector3 clone() {
        return new ImmutableVector3(this.x, this.y, this.z);
    }

    public float length() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public float distanceTo(ImmutableVector3 vector) {
        float xDistance = Math.abs(this.x - vector.getX());
        float yDistance = Math.abs(this.y - vector.getY());
        float zDistance = Math.abs(this.z - vector.getZ());
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2) + Math.pow(zDistance, 2));

        return distance;
    }

    //<editor-fold defaultstate="collapsed" desc="Addition">
    public ImmutableVector3 add(ImmutableVector3 addition) {
        return new ImmutableVector3(this.x + addition.getX(),
                this.y + addition.getY(),
                this.z + addition.getZ());
    }

    public ImmutableVector3 add(float x, float y, float z) {
        return new ImmutableVector3(this.x + x,
                this.y + y,
                this.z + z);
    }

    public ImmutableVector3 add(float addition) {
        return new ImmutableVector3(this.x + addition,
                this.y + addition,
                this.z + addition);
    }

    public ImmutableVector3 addToX(float addition) {
        return new ImmutableVector3(this.x + addition,
                this.y,
                this.z);
    }

    public ImmutableVector3 addToY(float addition) {
        return new ImmutableVector3(this.x,
                this.y + addition,
                this.z);
    }

    public ImmutableVector3 addToZ(float addition) {
        return new ImmutableVector3(this.x,
                this.y,
                this.z + addition);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Subtraction">
    public ImmutableVector3 subtract(ImmutableVector3 subtraction) {
        return new ImmutableVector3(this.x - subtraction.getX(),
                this.y - subtraction.getY(),
                this.z - subtraction.getZ());
    }

    public ImmutableVector3 subtract(float subtraction) {
        return new ImmutableVector3(this.x - subtraction,
                this.y - subtraction,
                this.z - subtraction);
    }

    public ImmutableVector3 subtractFromX(float subtraction) {
        return new ImmutableVector3(this.x - subtraction,
                this.y,
                this.z);
    }

    public ImmutableVector3 subtractFromY(float subtraction) {
        return new ImmutableVector3(this.x,
                this.y - subtraction,
                this.z);
    }

    public ImmutableVector3 subtractFromZ(float subtraction) {
        return new ImmutableVector3(this.x,
                this.y,
                this.z - subtraction);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Multiplication">
    public ImmutableVector3 multiplyBy(ImmutableVector3 multiplication) {
        return new ImmutableVector3(this.x * multiplication.getX(),
                this.y * multiplication.getY(),
                this.z * multiplication.getZ());
    }

    public ImmutableVector3 multiplyBy(float multiplication) {
        return new ImmutableVector3(this.x * multiplication,
                this.y * multiplication,
                this.z * multiplication);
    }

    public ImmutableVector3 multiplyXBy(float multiplication) {
        return new ImmutableVector3(this.x * multiplication,
                this.y,
                this.z);
    }

    public ImmutableVector3 multiplyYBy(float multiplication) {
        return new ImmutableVector3(this.x,
                this.y * multiplication,
                this.z);
    }

    public ImmutableVector3 multiplyZBy(float multiplication) {
        return new ImmutableVector3(this.x,
                this.y,
                this.z * multiplication);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Division">
    public ImmutableVector3 divideBy(ImmutableVector3 division) {
        return new ImmutableVector3(this.x / division.getX(),
                this.y / division.getY(),
                this.z / division.getZ());
    }

    public ImmutableVector3 divideBy(float division) {
        return new ImmutableVector3(this.x / division,
                this.y / division,
                this.z / division);
    }

    public ImmutableVector3 divideXBy(float division) {
        return new ImmutableVector3(this.x / division,
                this.y,
                this.z);
    }

    public ImmutableVector3 divideYBy(float division) {
        return new ImmutableVector3(this.x,
                this.y / division,
                this.z);
    }

    public ImmutableVector3 divideZBy(float division) {
        return new ImmutableVector3(this.x,
                this.y,
                this.z / division);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Negation">
    public ImmutableVector3 negate() {
        return new ImmutableVector3(-this.x, -this.y, -this.z);
    }

    public ImmutableVector3 negateX() {
        return new ImmutableVector3(-this.x, this.y, this.z);
    }

    public ImmutableVector3 negateY() {
        return new ImmutableVector3(this.x, -this.y, this.z);
    }

    public ImmutableVector3 negateZ() {
        return new ImmutableVector3(this.x, this.y, -this.z);
    }
    //</editor-fold>

    public ImmutableVector3 rotate(ImmutableVector3 eulerRotation) {
        Matrix4f rotationMatrix = MatrixMath.createRotationMatrix(eulerRotation);
        Vector4f extendedVector = new Vector4f(this.getX(), this.getY(), this.getZ(), 1.0f);
        Matrix4f.transform(rotationMatrix, extendedVector, extendedVector);
        ImmutableVector3 result = new ImmutableVector3(extendedVector.getX(), extendedVector.getY(), extendedVector.getZ());
        return result;
    }

    public boolean equals(ImmutableVector3 other, float tolerance) {
        return Math.abs(this.getX() - other.getX()) < tolerance
                && Math.abs(this.getY() - other.getY()) < tolerance
                && Math.abs(this.getZ() - other.getZ()) < tolerance;
    }
}
