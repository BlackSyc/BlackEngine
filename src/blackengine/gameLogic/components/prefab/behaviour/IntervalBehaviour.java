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
 *
 * @author Blackened
 */
public class IntervalBehaviour extends Behaviour {

    private long interval;

    private long lastTick;

    protected IntervalBehaviour(String name, Consumer<Entity> consumer, long delay, long interval, float priority) {
        super(name, consumer, delay, priority);
        this.interval = interval;
        this.lastTick = 0;
    }

    @Override
    public boolean tick() {
        if (LogicEngine.getInstance().getTimer().getCurrentTime() > this.lastTick + this.interval) {
            if (super.tick()) {
                this.lastTick = LogicEngine.getInstance().getTimer().getCurrentTime();
                return true;
            }
        }
        return false;
    }

}
