/* 
 * The MIT License
 *
 * Copyright 2017 Blackened.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
