/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.VERTEX_WEIGHTS;

/**
 *
 * @author Blackened
 */
public class VertexWeightVbo extends Vbo<float[]>{

    public VertexWeightVbo(float[] data) {
        super(3, data);
    }

    @Override
    public int getAttributeType() {
        return VERTEX_WEIGHTS.getValue();
    }
    
}
