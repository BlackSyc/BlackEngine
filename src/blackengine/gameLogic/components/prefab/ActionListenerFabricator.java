/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.movement.MoveDirection;
import blackengine.gameLogic.movement.TurnDirection;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action2;
import blackengine.userInput.InputAction;

/**
 * Can create a new ActionListenerComponent if presented with an inputManager
 * first.
 *
 * @author Blackened
 */
public enum ActionListenerFabricator implements ComponentFabricator<ActionListenerComponent> {

    /**
     * Creates a new ActionListenerComponent based on the default player
     * profile.
     * <br><br>
     * Requires the following usings:
     *
     * <ul>
     * <li>Observable&lt;InputAction&gt; inputActionSubject</li>
     * </ul>
     */
    DEFAULT_MOVEMENTLISTENER {

        private Observable<InputAction> inputActionSubject;

        /**
         * Creates a new instance of ActionListenerComponent using the preset
         * input action observable.
         *
         * @return
         */
        @Override
        public ActionListenerComponent create() {

            if (this.inputActionSubject != null) {
                List<InputAction> inputActionFilter = new ArrayList<>();
                inputActionFilter.add(InputAction.JUMP);
                inputActionFilter.add(InputAction.MOVE_FORWARD);
                inputActionFilter.add(InputAction.MOVE_BACKWARD);
                inputActionFilter.add(InputAction.TURN_LEFT);
                inputActionFilter.add(InputAction.TURN_RIGHT);
                inputActionFilter.add(InputAction.STRAFE_LEFT);
                inputActionFilter.add(InputAction.STRAFE_RIGHT);

                Action2<Entity, InputAction> action = (x, y) -> {

                    switch (y) {
                        case JUMP:
                            x.getComponent(MovementComponent.class).jump();
                            break;
                        case MOVE_FORWARD:
                            x.getComponent(MovementComponent.class).move(MoveDirection.FORWARD);
                            break;
                        case MOVE_BACKWARD:
                            x.getComponent(MovementComponent.class).move(MoveDirection.BACKWARD);
                            break;
                        case TURN_LEFT:
                            x.getComponent(MovementComponent.class).turn(TurnDirection.LEFT);
                            break;
                        case TURN_RIGHT:
                            x.getComponent(MovementComponent.class).turn(TurnDirection.RIGHT);
                            break;
                        case STRAFE_LEFT:
                            x.getComponent(MovementComponent.class).move(MoveDirection.LEFT);
                            break;
                        case STRAFE_RIGHT:
                            x.getComponent(MovementComponent.class).move(MoveDirection.RIGHT);
                            break;

                    }

                };
                return new ActionListenerComponent(this.inputActionSubject, inputActionFilter, action);
            };
            return null;

        }

        /**
         *
         * @param inputManager
         * @return
         */
        @Override
        public ActionListenerFabricator using(Observable<InputAction> inputActionSubject) {
            this.inputActionSubject = inputActionSubject;
            return this;
        }

    };

    @Override
    public abstract ActionListenerComponent create();

    public abstract ActionListenerFabricator using(Observable<InputAction> inputActionSubject);

}
