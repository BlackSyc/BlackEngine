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
package blackengine.openGL.vao.vbo;

import org.lwjgl.opengl.GL20;

/**
 *
 * @author Blackened
 */
public abstract class Vbo<T> {
    
    private final T data;
    private final int coordinateSize;
    private int vboId;

    public Vbo(int coordinateSize, T data) {
        this.coordinateSize = coordinateSize;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int getCoordinateSize() {
        return coordinateSize;
    }

    public int getVboId() {
        return vboId;
    }

    public void setVboId(int vboId) {
        this.vboId = vboId;
    }
    
    public void enable(){
        GL20.glEnableVertexAttribArray(this.getAttributeType());
    }
    
    public void disable(){
        GL20.glDisableVertexAttribArray(this.getAttributeType());
    }
    
    public abstract int getAttributeType();
    
}
