/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.gameLogic.movement;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public enum TurnDirection {
    
    LEFT {
        @Override
        protected Vector3f calculateRotation(float amount) {
            return new Vector3f(0, amount, 0);
        }
    },
    RIGHT {
        @Override
        protected Vector3f calculateRotation(float amount) {
            return new Vector3f(0, -1 * amount, 0);
        }
    };
    protected abstract Vector3f calculateRotation(float amount);
    
}
