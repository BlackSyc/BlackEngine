/* 
 * The MIT License
 *
 * Copyright 2017 Blackened.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.LogicEngine;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.gameLogic.movement.MoveDirection;
import blackengine.gameLogic.movement.TurnDirection;
import blackengine.toolbox.math.ImmutableVector3;

/**
 * An instance of this class will provide its parent with the ability to move.
 *
 * @author Blackened
 */
public class MovementComponent extends ComponentBase {

    private float movementSpeed;
    private float rotationSpeed;

    public MovementComponent(float movementSpeed, float rotationSpeed) {
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void move(MoveDirection direction) {
        ImmutableVector3 currentRotation = this.getParent().getTransform().getRelativeEulerRotation();
        ImmutableVector3 translation = direction.calculateTranslation(this.movementSpeed * LogicEngine.getInstance().getTimer().getDelta(), currentRotation);
        this.moveAbsolute(translation);
    }

    public void moveAbsolute(ImmutableVector3 translation) {
        ImmutableVector3 currentPosition = this.getParent().getTransform().getRelativePosition();
        ImmutableVector3 newPosition = currentPosition.add(translation);
        this.getParent().getTransform().setRelativePosition(newPosition);
    }

    public void turn(TurnDirection direction) {
        ImmutableVector3 translation = direction.calculateRotation(this.rotationSpeed * LogicEngine.getInstance().getTimer().getDelta());
        this.turnAbsolute(translation);
    }

    public void turnAbsolute(ImmutableVector3 rotation) {
        ImmutableVector3 newRotation = super.getParent().getTransform().getRelativeEulerRotation().add(rotation);
        super.getParent().getTransform().setRelativeEulerRotation(newRotation);
    }
    
    public MovementComponent copy(){
        return new MovementComponent(this.movementSpeed, this.rotationSpeed);
    }
}
