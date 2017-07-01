/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic.components;

/**
 * The base of all components used for the entity system of BlackEngine.
 *
 * @author Blackened
 */
public abstract class ComponentBase{

    /**
     * Whether this component is flagged for destruction.
     */
    private boolean destroyed = false;

    /**
     * Getter for whether this component is flagged for destruction.
     *
     * @return True if this component is flagged for destruction, false
     * otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Handles all logic necessary for destroying this component.
     */
    public void destroy() {
        this.destroyed = true;
    }

    public abstract void update();
    
    public Class<? extends ComponentBase> getMapping(){
        return this.getClass();
    }

}
