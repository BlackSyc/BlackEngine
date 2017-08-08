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
