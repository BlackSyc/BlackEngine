/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import blackengine.openGL.vao.vbo.IndexVbo;
import blackengine.openGL.vao.vbo.Vbo;
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

    public void setVaoId(int vaoId) {
        this.vaoId = vaoId;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public List<Vbo> getVbos() {
        return vbos;
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
