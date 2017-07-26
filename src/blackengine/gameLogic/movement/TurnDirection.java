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
        public Vector3f calculateRotation(float amount) {
            return new Vector3f(0,  amount, 0);
        }
    },
    RIGHT {
        @Override
        public Vector3f calculateRotation(float amount) {
            return new Vector3f(0, -1 * amount, 0);
        }
    };
    public abstract Vector3f calculateRotation(float amount);
    
}
