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
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Blackened
 */
public class VectorMath {

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Vector3f add(Vector3f original, Vector3f addition) {
        return new Vector3f(
                original.x + addition.x,
                original.y + addition.y,
                original.z + addition.z);
    }

    public static Vector2f add(Vector2f original, Vector2f addition) {
        return new Vector2f(
                original.x + addition.x,
                original.y + addition.y);
    }

    public static Vector3f multiply(Vector3f original, Vector3f multiplication) {
        return new Vector3f(
                original.x * multiplication.x,
                original.y * multiplication.y,
                original.z * multiplication.z);
    }

    public static Vector2f multiply(Vector2f original, Vector2f multiplication) {
        return new Vector2f(
                original.x * multiplication.x,
                original.y * multiplication.y);
    }

    public static Vector3f copyOf(Vector3f original) {
        return new Vector3f(original);
    }

    public static Vector2f copyOf(Vector2f original) {
        return new Vector2f(original);
    }

    public static float distance(ImmutableVector3 vector1, ImmutableVector3 vector2) {
        float xDistance = Math.abs(vector1.getX() - vector2.getX());
        float yDistance = Math.abs(vector1.getY() - vector2.getY());
        float zDistance = Math.abs(vector1.getZ() - vector2.getZ());
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2) + Math.pow(zDistance, 2));

        return distance;
    }

    public static float distance(Vector3f vector1, Vector3f vector2) {
        float xDistance = Math.abs(vector1.x - vector2.x);
        float yDistance = Math.abs(vector1.y - vector2.y);
        float zDistance = Math.abs(vector1.z - vector2.z);
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2) + Math.pow(zDistance, 2));

        return distance;
    }

    public static Vector3f rotateEuler(Vector3f vector, Vector3f eulerRotation) {
        Matrix4f rotationMatrix = MatrixMath.createRotationMatrix(eulerRotation);
        Vector4f extendedVector = new Vector4f(vector.getX(), vector.getY(), vector.getZ(), 1.0f);
        Matrix4f.transform(rotationMatrix, extendedVector, extendedVector);
        Vector3f result = new Vector3f(extendedVector.getX(), extendedVector.getY(), extendedVector.getZ());
        return result;
    }

}
