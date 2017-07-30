/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.base;

import blackengine.gameLogic.Entity;

/**
 * The base of all components used for the entity system of BlackEngine.
 *
 * @author Blackened
 */
public abstract class ComponentBase{
    
    private Entity parent;

    /**
     * Whether this component is flagged for destruction or not.
     */
    private boolean destroyed = false;
    
    private boolean active = false;

    /**
     * Getter for whether this component is flagged for destruction or not.
     *
     * @return True if this component is flagged for destruction, false
     * otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    public Entity getParent() {
        return parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    

    /**
     * Handles all logic necessary for destroying this component.
     */
    public void destroy() {
        this.parent = null;
        this.destroyed = true;
    }

    public abstract void update();
    
    public void activate(){
        this.active = true;
    }
    
    public void deactivate(){
        this.active = false;
    }
    
    public Class<? extends ComponentBase> getMapping(){
        return this.getClass();
    }

}
