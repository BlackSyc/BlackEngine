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
package blackengine.gameLogic.components.prefab.behaviour;

import blackengine.gameLogic.components.base.ComponentBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * An instance of this component class executes behaviours that have been added
 * to it in order of their priority.
 *
 * @author Blackened
 */
public class BehaviourComponent extends ComponentBase {

    /**
     * A list of all behaviours that will be called.
     */
    private ArrayList<Behaviour> behaviours;

    /**
     * Default constructor for creating a new instance of BehaviourComponent.
     */
    public BehaviourComponent() {
        this.behaviours = new ArrayList<>();
    }

    /**
     * Constructor for creating a new instance of BehaviourComponent.
     *
     * @param behaviours Any amount of instances of
     * {@link blackengine.gameLogic.components.prefab.behaviour.Behaviour Behaviour}
     * or its implementations.
     */
    public BehaviourComponent(Behaviour... behaviours) {
        this();
        Arrays.stream(behaviours).forEach(x -> this.addBehaviour(x));
    }

    /**
     * Adds a behaviour to this components behaviour list. Sets its entity
     * reference to the parent of this component.
     *
     * @param behaviour The instance of
     * {@link blackengine.gameLogic.components.prefab.behaviour.Behaviour Behaviour}
     * that will be added to this component.
     */
    public void addBehaviour(Behaviour behaviour) {
        behaviour.setEntity(this.getParent());
        this.behaviours.add(behaviour);
        this.behaviours.sort((x, y) -> Float.compare(x.getPriority(), y.getPriority()));
    }

    /**
     * Removes a behaviour from this component. Sets its entity reference to
     * null.
     *
     * @param behaviour The instance of
     * {@link blackengine.gameLogic.components.prefab.behaviour.Behaviour Behaviour}
     * that will be removed.
     */
    public void removeBehaviour(Behaviour behaviour) {
        behaviour.setEntity(null);
        this.behaviours.remove(behaviour);
    }

    /**
     * Removes a behaviour from this component. Sets its entity reference to
     * null.
     *
     * @param behaviourName An instance of String that represents the name of
     * the behaviour, and is defined as such in the behaviour that is to be
     * removed.
     */
    public void removeBehaviour(String behaviourName) {
        this.behaviours.removeIf(x -> {
            if (x.getName().equals(behaviourName)) {
                x.setEntity(null);
                return true;
            }
            return false;
        });
    }

    /**
     * Sets the {@link blackengine.gameLogic.Entity Entity} reference in all
     * behaviours in this component to this components parent.
     */
    @Override
    public void onActivate() {
        this.behaviours.forEach(x -> x.setEntity(this.getParent()));
    }

    /**
     * Removes all behaviours that have been flagged for destruction and calls
     * the
     * {@link blackengine.gameLogic.components.prefab.behaviour.Behaviour#tick()  tick()}
     * method on all behaviours that are present in this component.
     */
    @Override
    public void update() {
        this.behaviours.removeIf(x -> x.isDestroyed());
        this.behaviours.forEach(x -> x.tick());
    }
    
    @Override
    public BehaviourComponent clone(){
        return new BehaviourComponent((Behaviour[])this.behaviours.stream().map(x -> x.clone()).toArray());
    }

}
