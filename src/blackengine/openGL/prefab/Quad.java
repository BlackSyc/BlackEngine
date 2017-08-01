/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.openGL.prefab;

/**
 *
 * @author Blackened
 */
public final class Quad {

    private final float[] vertexPositions = {
        0f, 0f, 0f,
        1f, 0f, 0f,
        1f, 1f, 0f,
        0f, 1f, 0f
    };

    private final int[] indices = new int[]{
        0, 1, 2,
        2, 3, 0
    };

    private final float[] textureCoords = new float[]{
        0, 1,
        1f, 1f,
        1f, 0f,
        0f, 0f
    };

    public final int[] getIndices() {
        return indices;
    }

    public final float[] getVertexPositions() {
        return vertexPositions;
    }

    public final float[] getTextureCoords() {
        return textureCoords;
    }

}
