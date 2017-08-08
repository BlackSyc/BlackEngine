/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.LogicEngine;
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

    public MovementComponent(float movementSpeed, float rotationSpeed, float jumpPower) {
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.jumpPower = jumpPower;
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

    public void move(MoveDirection direction) {
        Vector3f currentRotation = this.getParent().getRotation();
        Vector3f translation = direction.calculateTranslation(this.movementSpeed * LogicEngine.getInstance().getTimer().getDelta(), currentRotation);
        this.moveAbsolute(translation);
    }

    public void moveAbsolute(Vector3f translation) {
        Vector3f currentPosition = this.getParent().getAbsolutePosition();
        Vector3f newPosition = currentPosition.translate(translation.x, translation.y, translation.z);
        this.getParent().setAbsolutePosition(newPosition);
    }

    public void turn(TurnDirection direction) {
        Vector3f translation = direction.calculateRotation(this.rotationSpeed * LogicEngine.getInstance().getTimer().getDelta());
        this.turnAbsolute(translation);
    }

    public void turnAbsolute(Vector3f rotation) {
        Vector3f newRotation = super.getParent().getRotation().translate(rotation.x, rotation.y, rotation.z);
        super.getParent().setRotation(newRotation);
    }

    /**
     *
     */
    @Override
    public void update() {

    }

}
