/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_POSITIONS;


/**
 *
 * @author Blackened
 */
public class VertexPositionVbo extends Vbo<float[]>{

    public VertexPositionVbo(float[] data) {
        super(3, data);
    }

    @Override
    public int getAttributeType() {
        return VERTEX_POSITIONS.getValue();
    }
    
}
