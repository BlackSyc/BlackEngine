/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.NORMAL_VECTORS;



/**
 *
 * @author Blackened
 */
public class NormalVectorVbo extends Vbo<float[]>{

    public NormalVectorVbo(float[] data) {
        super(3, data);
    }

    @Override
    public int getAttributeType() {
        return NORMAL_VECTORS.getValue();
    }
    
    
    
}
