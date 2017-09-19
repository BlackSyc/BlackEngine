/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.behaviour;

import blackengine.gameLogic.Entity;
import java.util.function.Consumer;

/**
 * An instance of this class can build a Behaviour with the provided parameters.
 *
 * @author Blackened
 */
public class BehaviourBuilder {

    /**
     * The name that will be passed to the behaviour when built.
     */
    private final String name;

    /**
     * The consumer that will be passed to the behaviour when built.
     */
    private final Consumer<Entity> consumer;

    /**
     * The delay that will be passed to the behaviour when built.
     */
    private long delay = 0;

    /**
     * The priority that will be passed to the behaviour when built.
     */
    private float priority = 1;

    /**
     * The interval that may be passed to a behaviour when built.
     */
    private long interval = 0;

    /**
     * The times the behaviour should repeat that may be passed to a behaviour
     * when built.
     */
    private int repeating = -1;

    /**
     * Default protected constructor for creating a new instance of
     * BehaviourBuilder.
     *
     * @param name The name of the behaviour when built.
     * @param consumer The consumer of the behaviour when built.
     */
    protected BehaviourBuilder(String name, Consumer<Entity> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    /**
     * Specify a delay for execution of the behaviours consumer.
     *
     * @param delay The delay in milliseconds.
     * @return this.
     */
    public BehaviourBuilder after(long delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Specify an interval which will occur between executions of the behaviours
     * consumer.
     *
     * @param interval The interval in milliseconds.
     * @return this.
     */
    public BehaviourBuilder every(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Specify the times the behaviour should execute its consumer before
     * flagging for destruction.
     *
     * @param times The times the behaviour should execute before flagging for
     * destruction.
     * @return this.
     */
    public BehaviourBuilder repeating(int times) {
        this.repeating = times;
        return this;
    }

    /**
     * Specify the priority of the behaviour that will be built. If not
     * specified, the priority will be 1.0f.
     *
     * @param priority The priority that will be given to the behaviour when
     * built.
     * @return this.
     */
    public BehaviourBuilder withPriority(float priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Builds the behaviour given all previously called method parameters.
     *
     * @return A new instance of
     * {@link blackengine.gameLogic.components.prefab.behaviour.Behaviour Behaviour}
     * with the specification that was specified using the parameters in
     * previously called builder methods.
     */
    public Behaviour build() {
        if (this.interval > 0) {
            if (this.repeating > -1) {
                return new RepeatingIntervalBehaviour(name, consumer, delay, interval, repeating, priority);
            }
            return new IntervalBehaviour(name, consumer, delay, interval, priority);
        }
        if (this.repeating > -1) {
            return new RepeatingBehaviour(name, consumer, delay, repeating, priority);
        }
        return new Behaviour(this.name, this.consumer, this.delay, this.priority);
    }

}
