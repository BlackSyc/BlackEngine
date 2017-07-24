/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.JOINT_INDICES;

/**
 *
 * @author Blackened
 */
public class JointIndexVbo extends Vbo<int[]>{

    public JointIndexVbo(int[] data) {
        super(1, data);
    }

    @Override
    public int getAttributeType() {
        return JOINT_INDICES.getValue();
    }
    
}
