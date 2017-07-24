/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.INDEX;

/**
 *
 * @author Blackened
 */
public class IndexVbo extends Vbo<int[]>{

    public IndexVbo(int[]data) {
        super(1, data);
    }

    @Override
    public int getAttributeType() {
        return INDEX.getValue();
    }    
}
