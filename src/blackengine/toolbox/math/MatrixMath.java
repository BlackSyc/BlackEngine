/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
     * @param rotation The Euler rotation.
     * @param scale The scale.
     * @return A new transformation matrix.
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    /**
     * Creates a matrix that contains the translation, rotation and scale
     * properties.
     *
     * @param translation The position.
     * @param width
     * @param height
     * @return A new transformation matrix.
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation, float width, float height) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(width, height, 1), matrix, matrix);
        return matrix;
    }

}
