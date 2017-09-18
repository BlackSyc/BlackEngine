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
public class Behaviour {
    
    private Entity entity;
    
    private float priority;
    
    private String name;
    
    private Consumer<Entity> consumer;
    
    private long delay;
    
    private boolean delayPassed = false;
    
    private final long timeAfterDelay;
    
    private boolean destroyed = false;
    
    public void destroy(){
        this.destroyed = true;
    }

    public String getName() {
        return name;
    }    

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setEntity(Entity parent) {
        this.entity = parent;
    }
    
    protected Behaviour(String name, Consumer<Entity> consumer, long delay, float priority) {
        this.name = name;
        this.consumer = consumer;
        if(delay < 0){
            this.delayPassed = true;
            this.delay = 0;
        }
        this.timeAfterDelay = LogicEngine.getInstance().getTimer().getCurrentTime() + delay;
        this.delay = delay;
    }

    public float getPriority() {
        return priority;
    }
    
    public boolean tick(){
        if(this.delayPassed){
            this.consumer.accept(this.entity);
            return true;
        }
        else if(LogicEngine.getInstance().getTimer().getCurrentTime() > this.timeAfterDelay){
            this.consumer.accept(this.entity);
            this.delayPassed = true;
            return true;
        }
        return false;
    }
    
    public static BehaviourBuilder as(String name, Consumer<Entity> consumer){
        return new BehaviourBuilder(name, consumer);
    }
    
    
}
