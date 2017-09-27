/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.behaviour;

import blackengine.gameLogic.Entity;
import java.util.function.Consumer;

/**
 *
 * @author Blackened
 */
public class RepeatingBehaviour extends Behaviour {

    private final int times;

    private int timesPassed;

    protected RepeatingBehaviour(String name, Consumer<Entity> consumer, long delay, int times, float priority) {
        super(name, consumer, delay, priority);
        this.times = times;
        this.timesPassed = 0;
    }

    @Override
    public boolean tick() {
        if (this.timesPassed < this.times) {
            if (super.tick()) {
                this.timesPassed++;
                return true;
            }
        } else {
            this.destroy();
        }
        return false;
    }

}
