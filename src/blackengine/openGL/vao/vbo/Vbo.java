/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
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
