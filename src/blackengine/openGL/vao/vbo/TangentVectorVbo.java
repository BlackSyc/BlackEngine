/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.TANGENT_VECTORS;


/**
 *
 * @author Blackened
 */
public class TangentVectorVbo extends Vbo<float[]>{

    public TangentVectorVbo(float[] data) {
        super(3, data);
    }

    @Override
    public int getAttributeType() {
        return TANGENT_VECTORS.getValue();
    }
    
}
