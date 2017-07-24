/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.gameLogic.movement.MoveDirection;
import blackengine.gameLogic.movement.TurnDirection;
import org.lwjgl.util.vector.Vector3f;

/**
 * An instance of this class will provide its parent with the ability to move.
 *
 * @author Blackened
 */
public class MovementComponent extends ComponentBase {

    private float movementSpeed;
    private float rotationSpeed;
    private float upwardsSpeed = 0;
    private float jumpPower;
    private boolean canFly = false;

    public MovementComponent(float movementSpeed, float rotationSpeed, float jumpPower, boolean canFly) {
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.jumpPower = jumpPower;
        this.canFly = canFly;
    }

    public float getUpwardsSpeed() {
        return upwardsSpeed;
    }

    public void setUpwardsSpeed(float upwardsSpeed) {
        this.upwardsSpeed = upwardsSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(float jumpPower) {
        this.jumpPower = jumpPower;
    }

    public void increaseUpwardsSpeed(float increase) {
        this.upwardsSpeed += increase;
    }

    /**
     * Moves its parent in the specified direction. Frame-time is taken into
     * account for this calculation.
     *
     * @param direction
     */
    public void move(MoveDirection direction) {
    }

    public void moveAbsolute(MoveDirection direction) {
    }

    public void turn(TurnDirection direction) {
    }

    public void turnAbsolute(float amount) {
    }

    private void translateParentPosition(Vector3f translation) {
    }

    private void translateParentRotation(Vector3f translation) {
    }

    public void jump() {
    }

    /**
     *
     */
    @Override
    public void update() {

    }

}
