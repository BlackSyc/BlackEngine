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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import blackengine.openGL.vao.vbo.IndexVbo;
import blackengine.openGL.vao.vbo.Vbo;
import java.util.Collections;
import java.util.stream.Stream;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Blackened
 */
public class Vao {
    
    private int vaoId;
    
    private final List<Vbo> vbos;
    
    private final IndexVbo index;
    
    private final int vertexCount;

    protected Vao(int vertexCount, IndexVbo index, Vbo... vbos) {
        this.vertexCount = vertexCount;
        this.index = index;
        this.vbos = new ArrayList<>();
        this.vbos.addAll(Arrays.asList(vbos));
    } 

    protected void setVaoId(int vaoId) {
        this.vaoId = vaoId;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Stream<Vbo> getVbos() {
        return vbos.stream();
    }

    public IndexVbo getIndex() {
        return index;
    }
    
    public void bind(){
        GL30.glBindVertexArray(this.getVaoId());
        this.vbos.forEach(x -> x.enable());
    }
    
    public void unbind(){
        this.vbos.forEach(x -> x.disable());
    }
    
    
}
