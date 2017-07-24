/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.toolbox.math;

import org.lwjgl.util.vector.Matrix4f;

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

}
