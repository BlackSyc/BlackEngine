/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

import static blackengine.openGL.vao.vbo.AttributeType.TEXTURE_COORDS;

/**
 *
 * @author Blackened
 */
public class TextureCoordVbo extends Vbo<float[]>{

    public TextureCoordVbo(float[] data) {
        super(2, data);
    }

    @Override
    public int getAttributeType() {
        return TEXTURE_COORDS.getValue();
    }
    
}
