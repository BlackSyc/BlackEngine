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
public abstract class ComponentBase {

    /**
     * The parent entity this component belongs to.
     */
    private Entity parent;

    /**
     * Whether this component is flagged for destruction or not.
     */
    private boolean destroyed = false;

    /**
     * Whether this component is activated or not.
     */
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

    /**
     * Getter for the parent of this component.
     *
     * @return The parent entity this component belongs to.
     */
    public Entity getParent() {
        return parent;
    }

    /**
     * Setter for the parent of this component.
     *
     * @param parent The parent entity this component belongs to.
     */
    public void setParent(Entity parent) {
        this.parent = parent;
    }

    /**
     * Getter for the active flag.
     *
     * @return Whether the component is activated or not. True when it was
     * activated, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Deactivates this component, sets its parent to null and flags for
     * destruction.
     */
    public void destroy() {
        this.deactivate();
        this.parent = null;
        this.destroyed = true;

    }

    /**
     * Update method will be called every frame if this implementation of
     * ComponentBase was registered in the
     * {@link blackengine.gameLogic.LogicEngine LogicEngine}.
     */
    public abstract void update();

    /**
     * Activates this component by settings its active flag to true.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivates this component by setting its active flag to false.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Retrieves the Class that will be used to map update priority in the
     * {@link blackengine.gameLogic.LogicEngine LogicEngine}.
     *
     * @return A class extending ComponentBase.
     */
    public Class<? extends ComponentBase> getMapping() {
        return this.getClass();
    }

}
