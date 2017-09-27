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

/**
 *
 * @author Blackened
 */
public class MatrixMath {

    /**
     * Changes the rotation part of the matrix to the inverse of the rotation
     * part of the view matrix.
     *
     * @param matrix The matrix to change.
     * @param viewMatrix The viewMatrix to use as reference to calculate the
     * inverse rotation in the original matrix.
     */
    public static void rotateMatrixTowardsCamera(
            Matrix4f matrix, Matrix4f viewMatrix) {
        matrix.m00 = viewMatrix.m00;
        matrix.m01 = viewMatrix.m10;
        matrix.m02 = viewMatrix.m20;
        matrix.m10 = viewMatrix.m01;
        matrix.m11 = viewMatrix.m11;
        matrix.m12 = viewMatrix.m21;
        matrix.m20 = viewMatrix.m02;
        matrix.m21 = viewMatrix.m12;
        matrix.m22 = viewMatrix.m22;
    }

    /**
     * Creates a matrix that contains the translation, rotation and scale
     * properties.
     *
     * @param translation The position.
     * @param eulerRotation The Euler rotation in degrees.
     * @param scale The scale.
     * @return A new transformation matrix.
     */
    public static Matrix4f createTransformationMatrix(ImmutableVector3 translation, ImmutableVector3 eulerRotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation.mutable(), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getX()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getY()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getZ()), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    /**
     * Creates a matrix that contains the translation, rotation and scale
     * properties.
     *
     * @param translation The position.
     * @param eulerRotation The Euler rotation in degrees.
     * @param scale The scale.
     * @return A new transformation matrix.
     */
    public static Matrix4f createTransformationMatrix(ImmutableVector3 translation, ImmutableVector3 eulerRotation, ImmutableVector3 scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation.mutable(), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getX()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getY()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getZ()), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(scale.mutable(), matrix, matrix);
        return matrix;
    }
    
    public static Matrix4f createRotationMatrix(ImmutableVector3 eulerRotation) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getX()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getY()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(eulerRotation.getZ()), new Vector3f(0, 0, 1), matrix, matrix);
        return matrix;
    }
}
