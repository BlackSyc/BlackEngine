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
public class RepeatingIntervalBehaviour extends IntervalBehaviour {

    private final int times;

    private int timesPassed;

    protected int getTimes() {
        return times;
    }
    
    

    protected RepeatingIntervalBehaviour(String name, Consumer<Entity> consumer, long delay, long interval, int times, float priority) {
        super(name, consumer, delay, interval, priority);
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
        }
        else{
            this.destroy();
        }
        return false;
    }
    
    public RepeatingIntervalBehaviour clone(){       
        return new RepeatingIntervalBehaviour(this.getName(), 
                this.getConsumer(), 
                this.getDelay(), 
                this.getInterval(), 
                this.times, 
                this.getPriority());
    }

}
