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
public class BehaviourBuilder {
    
    private final String name;
        
        private final Consumer<Entity> consumer;
        
        private long delay = 0;
        
        private float priority = 0;
        
        private long interval = 0;
        
        private int repeating = -1;

        protected BehaviourBuilder(String name, Consumer<Entity> consumer) {
            this.name = name;
            this.consumer = consumer;
        }
        
        public BehaviourBuilder after(long delay){
            this.delay = delay;
            return this;
        }
        
        public BehaviourBuilder every(long interval){
            this.interval = interval;
            return this;
        }
        
        public BehaviourBuilder repeating(int times){
            this.repeating = times;
            return this;
        }
        
        public BehaviourBuilder withPriority(float priority){
            this.priority = priority;
            return this;
        }
        
        public Behaviour build(){
            if(this.interval > 0){
                if(this.repeating > -1){
                    return new RepeatingIntervalBehaviour(name, consumer, delay, interval, repeating, priority);
                }
                return new IntervalBehaviour(name, consumer, delay, interval, priority);
            }
            if(this.repeating > -1){
                return new RepeatingBehaviour(name, consumer, delay, repeating, priority);
            }
            return new Behaviour(this.name, this.consumer, this.delay, this.priority);
        }
    
}
