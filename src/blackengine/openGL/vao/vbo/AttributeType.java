/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.openGL.vao.vbo;

/**
 *
 * @author Blackened
 */
public enum AttributeType {

    INDEX(-1),
    VERTEX_POSITIONS(0),
    TEXTURE_COORDS(1),
    NORMAL_VECTORS(2),
    TANGENT_VECTORS(3),
    JOINT_INDICES(4),
    VERTEX_WEIGHTS(5);

    private final int value;

    AttributeType(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

}
