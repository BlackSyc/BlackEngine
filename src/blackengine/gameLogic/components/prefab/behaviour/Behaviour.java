/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.behaviour;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.LogicEngine;
import java.util.function.Consumer;

/**
 * An instance of this class represents a behavioural function that will be
 * called by the parent
 * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}
 * on its parent entity.
 *
 * @author Blackened
 */
public class Behaviour {

    /**
     * The entity this behaviour is an indirect child of.
     */
    private Entity entity;

    /**
     * The priority determines the order in which behaviours are being called
     * from the
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}.
     */
    private float priority;

    /**
     * The name of this behaviour, this can be used to remove it from its parent
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}.
     */
    private String name;

    /**
     * The consumer that will be called when this behaviour successfully ticks.
     */
    private Consumer<Entity> consumer;

    /**
     * The delay before the first tick will result in calling the consumer.
     */
    private long delay;

    /**
     * A boolean representing whether the behaviour should check if the delay
     * has passed (true) or if it should still be checked (false).
     */
    private boolean delayPassed = false;

    /**
     * The delay + the time when this behaviour was created. This is used to
     * determine whether the delay has passed yet.
     */
    private final long timeAfterDelay;

    /**
     * Flags this behaviour for destruction if true, so it will be removed by
     * the parent
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}.
     */
    private boolean destroyed = false;

    /**
     * Flags this behaviour for destruction. It will be removed from the parent
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}
     * in the next update cycle.
     */
    public void destroy() {
        this.destroyed = true;
    }

    /**
     * Getter for the name of this behaviour.
     *
     * @return A String representing the name of this behaviour.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the destruction flag of this behaviour.
     *
     * @return A boolean indicating whether the behaviour should be destroyed by
     * its parent
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}
     * on next update cycle. True if it should be destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Setter for the {@link blackengine.gameLogic.Entity Entity} reference for
     * this behaviour.
     *
     * @param entity An instance of {@link blackengine.gameLogic.Entity Entity}.
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Getter for the priority of this behaviour. A higher priority means the
     * behaviour will be called before other behaviours in the parent
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourComponent BehaviourComponent}
     * update cycle.
     *
     * @return A float representing the priority of this behaviour.
     */
    public float getPriority() {
        return priority;
    }

    /**
     * Default protected constructor for creating a new instance of Behaviour.
     *
     * @param name The name of the behaviour.
     * @param consumer The consumer that will be called each successful tick.
     * @param delay The delay before the first successful tick should happen.
     * @param priority The priority of this behaviour.
     */
    protected Behaviour(String name, Consumer<Entity> consumer, long delay, float priority) {
        this.name = name;
        this.consumer = consumer;
        if (delay < 0) {
            this.delayPassed = true;
            this.delay = 0;
        }
        this.timeAfterDelay = LogicEngine.getInstance().getTimer().getCurrentTime() + delay;
        this.delay = delay;
    }

    /**
     * Will call the consumer if the delay has passed. Will return true if the
     * consumer was called, false otherwise.
     *
     * @return A boolean representing whether the consumer was called.
     */
    public boolean tick() {
        if (this.delayPassed) {
            this.consumer.accept(this.entity);
            return true;
        } else if (LogicEngine.getInstance().getTimer().getCurrentTime() > this.timeAfterDelay) {
            this.consumer.accept(this.entity);
            this.delayPassed = true;
            return true;
        }
        return false;
    }

    /**
     * Creates a new behaviour builder. Specify behaviour parameters after this
     * call, like {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourBuilder#after(long) after(delayInMs)},
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourBuilder#every(long)  every(intervalInMs)}
     * or
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourBuilder#repeating(int)  repeating(times)}.
     * End the build by calling
     * {@link blackengine.gameLogic.components.prefab.behaviour.BehaviourBuilder#build()  build()}.
     *
     * @param name The name of the behaviour that will be built.
     * @param consumer The consumer that will be called each successful tick.
     * @return
     */
    public static BehaviourBuilder as(String name, Consumer<Entity> consumer) {
        return new BehaviourBuilder(name, consumer);
    }

}
