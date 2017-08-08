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
package blackengine.dataAccess.dataObjects;

/**
 * An instance of this class represents a mesh loaded in from disk through the
 * MeshLoader. An instance of this class is immutable.
 *
 * @author Blackened
 */
public class MeshDataObject {

    /**
     * The number of dimensions this mesh is in.
     */
    private final int numberOfDimensions;

    /**
     * The positions of all vertices.
     */
    private final float[] vertexPositions;

    /**
     * The coordinates of the texture.
     */
    private final float[] textureCoords;

    /**
     * The normal vectors.
     */
    private final float[] normals;

    /**
     * The indices of the model.
     */
    private final int[] indices;

    /**
     * Getter for the vertex position array.
     *
     * @return A float array containing all coordinates of all vertices.
     */
    public float[] getVertexPositions() {
        return vertexPositions;
    }

    /**
     * Getter for the texture coordinate array.
     *
     * @return A float array containing all texture coordinates of all vertices.
     */
    public float[] getTextureCoords() {
        return textureCoords;
    }

    /**
     * Getter for the normal vector array.
     *
     * @return A float array containing all normal vectors.
     */
    public float[] getNormals() {
        return normals;
    }

    /**
     * Getter for the indices array.
     *
     * @return An integer array containing all indices for this mesh.
     */
    public int[] getIndices() {
        return indices;
    }

    /**
     * Default constructor for creating a new instance of MeshDataObject.
     *
     * @param vertexPositions The position coordinates of all vertices in a
     * float array.
     * @param textureCoords The texture coordinates of all vertices in a float
     * array.
     * @param normals The normal vectors in a float array.
     * @param indices The indices in an integer array.
     * @param numberOfDimensions The number of dimensions this mesh is in.
     */
    public MeshDataObject(float[] vertexPositions, float[] textureCoords, float[] normals, int[] indices, int numberOfDimensions) {
        this.vertexPositions = vertexPositions;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.numberOfDimensions = numberOfDimensions;
    }

    /**
     * Calculates the vertex count by dividing the amount of vertex coordinates
     * by the number of dimensions.
     *
     * @return An integer representing the amount of vertices that are present
     * in this mesh.
     */
    public int calculateVertexCount() {
        return this.vertexPositions.length / this.numberOfDimensions;
    }

}
