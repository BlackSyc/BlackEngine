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
package blackengine.openGL.vao;

import blackengine.dataAccess.dataObjects.MeshDataObject;
import blackengine.openGL.prefab.Quad;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import blackengine.openGL.vao.vbo.IndexVbo;
import blackengine.openGL.vao.vbo.NormalVectorVbo;
import blackengine.openGL.vao.vbo.TextureCoordVbo;
import blackengine.openGL.vao.vbo.Vbo;
import blackengine.openGL.vao.vbo.VertexPositionVbo;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Blackened
 */
public class VaoLoader {

    public static Vao loadVAO(MeshDataObject meshData) {

        //First we create all Vbos that are needed
        IndexVbo indexVbo = new IndexVbo(meshData.getIndices());

        VertexPositionVbo vertexPositionVbo = new VertexPositionVbo(meshData.getVertexPositions());
        TextureCoordVbo textureCoordVbo = new TextureCoordVbo(meshData.getTextureCoords());
        NormalVectorVbo normalVectorVbo = new NormalVectorVbo(meshData.getNormals());

        //Then we create the VAO
        int vertexCount = indexVbo.getData().length;
        Vao meshVao = new Vao(vertexCount, indexVbo, vertexPositionVbo, textureCoordVbo, normalVectorVbo);

        // Then we create the Vao on the graphics card, and bind it.
        int vaoID = GL30.glGenVertexArrays();
        meshVao.setVaoId(vaoID);
        GL30.glBindVertexArray(meshVao.getVaoId()); // This might not be needed. TODO: CHECK THIS.

        // Now we create all Vbos on the graphics card, and add them to the Vao.
        attachIndexVbo(meshVao.getIndex());
        meshVao.getVbos().forEach(x -> attachVbo(x));

        // Unbind the VAO
        GL30.glBindVertexArray(0);

        return meshVao;
    }

    public static Vao loadVao(Quad quad) {

        IndexVbo indexVbo = new IndexVbo(quad.getIndices());

        VertexPositionVbo vertexPositionVbo = new VertexPositionVbo(quad.getVertexPositions());
        TextureCoordVbo textureCoordVbo = new TextureCoordVbo(quad.getTextureCoords());

        int vertexCount = indexVbo.getData().length;
        Vao quadVao = new Vao(vertexCount, indexVbo, vertexPositionVbo, textureCoordVbo);

        int vaoID = GL30.glGenVertexArrays();
        quadVao.setVaoId(vaoID);
        GL30.glBindVertexArray(quadVao.getVaoId());

        attachIndexVbo(quadVao.getIndex());
        quadVao.getVbos().forEach(x -> attachVbo(x));

        GL30.glBindVertexArray(0);

        return quadVao;

    }

    /**
     * Creates the IndexVBO for the currently bound VAO.
     *
     * @param data
     */
    private static void attachIndexVbo(IndexVbo indexVbo) {
        // Create new VBO.
        int vboID = GL15.glGenBuffers();

        // Bind the new VBO.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        // Converts int array to an instance of IntBuffer, which can
        // be stored in a VBO.
        IntBuffer buffer = toReadableIntBuffer(indexVbo.getData());

        // Puts the buffer into the VBO, and GL_STATIC_DRAW tells it that it 
        // won't ever be modified.
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Unbind the VBO. TODO: CHECK IF THIS IS NECESSARY:
        //GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        indexVbo.setVboId(vboID);
    }

    private static void attachVbo(Vbo vbo) {

        // Create new VBO.
        int vboID = GL15.glGenBuffers();

        // Bind the new VBO.
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        if (vbo.getData() instanceof float[]) {

            // Converts float array to an instance of FloatBuffer, which can
            // be stored in a VBO.
            FloatBuffer buffer = toReadableFloatBuffer((float[]) vbo.getData());

            // Puts the buffer into the VBO, and GL_STATIC_DRAW tells it that it 
            // won't ever be modified.
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

            // Specifies that this is for the Vertex Array.
            GL20.glVertexAttribPointer(vbo.getAttributeType(), vbo.getCoordinateSize(), GL11.GL_FLOAT, false, 0, 0);
        }
        if (vbo.getData() instanceof int[]) {

            IntBuffer buffer = toReadableIntBuffer((int[]) vbo.getData());

            throw new UnsupportedOperationException("loading int buffers is not yet implemented!");
        }

        // Unbind the VBO. TODO: CHECK IF THIS IS NECESSARY:
        //GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        vbo.setVboId(vboID);
    }

    private static FloatBuffer toReadableFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        // Make readable
        buffer.flip();
        return buffer;
    }

    private static IntBuffer toReadableIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        // Make readable
        buffer.flip();
        return buffer;
    }

}
