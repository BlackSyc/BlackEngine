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
package blackengine.gameLogic;

import static blackengine.gameLogic.DefaultTag.NONE;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.gameLogic.exceptions.DuplicateComponentTypeException;
import blackengine.gameLogic.exceptions.DuplicateEntityNameException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;

/**
 * An instance of this class represents an entity in 3D space. This entity can
 * consist of multiple other child entities and components for itself.
 *
 * #Tested
 *
 * @author Blackened
 */
public class Entity {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private Tag tag;

    /**
     * The name of this particular entity object.
     */
    private String name;

    /**
     * The parent of this entity.
     */
    private Entity parent;

    /**
     * A reference to the game element containing this entity.
     */
    private GameElement gameElement;

    /**
     * Whether this instance is flagged for destruction or not.
     */
    private boolean destroyed = false;

    /**
     * A map of all child entities mapped to their name.
     */
    private final Map<String, Entity> children;

    private Transform transform;

    /**
     * All components belonging to this entity, mapped to their mapping class.
     */
    private final Map<Class<? extends ComponentBase>, ComponentBase> components;

    private boolean active = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Getter for the name of this entity.
     *
     * @return A String with the name of this entity.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the parent of this entity.
     *
     * @return The parent of this entity, or null if none is specified.
     */
    public Entity getParent() {
        return parent;
    }

    /**
     * Getter for the game element that is containing this entities parent, or
     * if no parent is set, this entity itself.
     *
     * @return An instance of GameElement.
     */
    public GameElement getGameElement() {
        return this.getParent() != null ? this.getParent().getGameElement() : this.gameElement;
    }

    /**
     * Getter for whether the entity is flagged for destruction or not.
     *
     * @return True if this entity was flagged for destruction, false otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    public Transform getTransform() {
        return transform;
    }

    /**
     * Setter for the parent of this entity.
     *
     * @param parent The parent of this entity.
     */
    public void setParent(Entity parent) {
        this.parent = parent;
    }

    /**
     * Setter for the game element containing this entity.
     *
     * @param gameElement An instance of
     * {@link blackengine.gameLogic.GameElement GameElement} containing this
     * entity.
     */
    public void setGameElement(GameElement gameElement) {
        this.gameElement = gameElement;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isActive() {
        return active;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of the Entity class.
     *
     * @param name The name of this particular entity.
     * @param position The position of this particular entity in 3D space.
     * @param rotation The rotation of this particular entity.
     * @param scale The scale of this entity.
     */
    public Entity(String name, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.name = name;
        this.components = new HashMap<>();
        this.children = new HashMap<>();
        this.tag = NONE;
        this.transform = new Transform(this, position, rotation, scale);
    }

    /**
     * Constructor for creating a new instance of the Entity class. The position
     * and rotation will be set to 0, the scale will be set to 1.
     *
     * @param name The name of the entity.
     */
    public Entity(String name) {
        this(name, new Vector3f(), new Vector3f(), new Vector3f(1, 1, 1));
    }

    /**
     * Constructor for creating a new instance of the Entity class. The rotation
     * will be set to 0, the scale will be set to 1.
     *
     * @param name The name of the entity.
     * @param position The position of the entity.
     */
    public Entity(String name, Vector3f position) {
        this(name, position, new Vector3f(), new Vector3f(1, 1, 1));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Verifies whether a child with the specified name is present in this
     * entity.
     *
     * @param name The name of the child for which its presence will be checked.
     * @return True when a child with the specified name is present, false
     * otherwise.
     */
    public boolean containsChild(String name) {
        return this.children.containsKey(name);
    }

    /**
     * Retrieves an entity with the specified name if it was present in this
     * entity.
     *
     * @param name The name of the entity to be retrieved.
     * @return An entity with the specified name, or null if no match was found.
     */
    public Entity getChild(String name) {
        return this.children.get(name);
    }

    /**
     * Adds a child entity to this entity. If one with the same name was already
     * present, a
     * {@link blackengine.gameLogic.exceptions.DuplicateEntityNameException DuplicateEntityNameException}
     * will be thrown. If the parent entity was flagged active, the child will
     * be activated as well.
     *
     * @param child An entity that will be added to this entity as a child.
     */
    public void addChild(Entity child) throws DuplicateEntityNameException {
        if (child != null) {
            if (this.containsChild(child.getName())) {
                throw new DuplicateEntityNameException();
            } else {
                this.children.put(child.getName(), child);
                child.setParent(this);
                if (this.active) {
                    child.activate();
                }
            }

        }
    }

    /**
     * Detaches a child with the specified name from this entity and returns it.
     *
     * @param name The name of the child that is to be removed.
     * @return The child that was removed from this entity.
     */
    public Entity detachChild(String name) {
        Entity child = this.children.get(name);
        this.children.remove(name);
        child.setParent(null);
        return child;
    }

    /**
     * Destroys and remove the child entity specified by its name from this
     * entity.
     *
     * @param name The name of the child entity that is to be destroyed and
     * removed.
     */
    public void destroyChild(String name) {
        Entity child = this.getChild(name);
        if (child != null) {
            child.destroy();
            this.removeChildrenFlaggedForDestruction();
        }
    }

    /**
     * Verifies whether a component of the specified class is present in this
     * entity.
     *
     * @param clazz The class which will be used to check whether the component
     * is present.
     * @return True if a component is present of the desired class, false
     * otherwise.
     */
    public boolean containsComponent(Class<? extends ComponentBase> clazz) {
        return this.components.containsKey(clazz);
    }

    /**
     * Retrieves a component of the specified class if it is present in this
     * entity.
     *
     * @param <T> The type of component that should be returned.
     * @param clazz The class of the component that should be returned.
     * @return A component from this entity of the specified class.
     */
    public <T extends ComponentBase> T getComponent(Class<T> clazz) {
        return clazz.cast(this.components.get(clazz));
    }

    /**
     * Retrieved all components in the entity's component collection.
     *
     * @return A collection of all the components.
     */
    public Collection<ComponentBase> getAllComponents() {
        return this.components.values();
    }

    /**
     * Adds a component to this entity if a component with this mapping was not
     * yet present. If one was already present, a
     * {@link blackengine.gameLogic.exceptions.DuplicateComponentTypeException DuplicateComponentTypeException}
     * will be thrown. If the entity is flagged active, the component will be
     * activated as well.
     *
     * @param component The component to be added to this entity.
     */
    public void addComponent(ComponentBase component) throws DuplicateComponentTypeException {
        if (component != null) {
            if (this.containsComponent(component.getMapping())) {
                throw new DuplicateComponentTypeException();
            } else {
                component.setParent(this);
                this.components.put(component.getMapping(), component);
                if (this.active) {
                    component.activate();
                }
            }

        }
    }

    /**
     * Detaches a component of the specified class from this entity if it is
     * present in this entity.
     *
     * @param <T> The type of the component.
     * @param clazz The class of the component that should be detached.
     * @return The component that was detached.
     */
    public <T extends ComponentBase> T detachComponent(Class<T> clazz) {
        T component = this.getComponent(clazz);
        this.components.remove(clazz);
        component.setParent(null);
        return component;
    }

    /**
     * Destroys and removes the component specified by its class from this
     * entity.
     *
     * @param clazz The class of the component that is to be destroyed and
     * removed.
     */
    public void destroyComponent(Class<? extends ComponentBase> clazz) {
        if (this.containsComponent(clazz)) {
            this.getComponent(clazz).destroy();
            this.removeComponentsFlaggedForDestruction();
        }
    }

    /**
     * Updates all children entities and components.
     */
    public void update() {
        Iterator<Class<? extends ComponentBase>> iter = LogicEngine.getInstance().getComponentIterator();

        while (iter.hasNext()) {
            Class<? extends ComponentBase> componentClass = iter.next();
            if (this.components.containsKey(componentClass)) {
                this.components.get(componentClass).update();
            }
        }

        // Update all children.
        this.children.values().forEach(x -> x.update());
    }

    /**
     * Gets called after the {@link #update() update()} method. Also removes all
     * components and children that have been flagged for destruction.
     */
    public void lateUpdate() {
        // LateUpdate all components that are present in the order presented by the
        // order iterator. Removes the components if they are flagged for 
        // destruction.
        Iterator<Class<? extends ComponentBase>> iter = LogicEngine.getInstance().getComponentIterator();

        while (iter.hasNext()) {
            Class<? extends ComponentBase> componentClass = iter.next();
            if (this.components.containsKey(componentClass)) {
                ComponentBase currentComponent = this.components.get(componentClass);
                currentComponent.lateUpdate();
                if (currentComponent.isDestroyed()) {
                    this.components.remove(currentComponent.getMapping());
                }
            }
        }

        // Update all children.
        this.children.values().forEach(x -> x.lateUpdate());

        // Remove all children that are flagged for destruction.
        this.removeChildrenFlaggedForDestruction();
    }

    public void activate() {
        this.children.values().forEach(x -> x.activate());
        this.components.values().forEach(x -> x.activate());
        this.active = true;
    }

    public void deactivate() {
        this.children.values().forEach(x -> x.deactivate());
        this.components.values().forEach(x -> x.deactivate());
        this.active = false;
    }

    /**
     * Destroys this entity and all its components and children and flags for
     * destruction.
     */
    public void destroy() {
        this.children.values().forEach(x -> x.destroy());
        this.removeChildrenFlaggedForDestruction();
        this.components.values().forEach(x -> x.destroy());
        this.removeComponentsFlaggedForDestruction();
        this.destroyed = true;

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Removes all components from this entity that are flagged for destruction.
     */
    private void removeComponentsFlaggedForDestruction() {
        this.components.entrySet().removeIf(x -> x.getValue().isDestroyed());
    }

    /**
     * Removes all children from this entity that are flagged for destruction.
     */
    private void removeChildrenFlaggedForDestruction() {
        this.children.entrySet().removeIf(x -> x.getValue().isDestroyed());
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Static Methods">
    /**
     * Creates an instance of Entity, and activates it and its components.
     *
     * @param name The name of the entity.
     * @param position The position of the entity.
     * @param rotation The rotation of the entity.
     * @param scale The scale of the entity.
     * @param components The components that will be added to this entity.
     * @return The entity.
     */
    public static Entity create(String name, Vector3f position, Vector3f rotation, Vector3f scale, ComponentBase... components) {
        Entity entity = new Entity(name, position, rotation, scale);
        if (components != null) {
            Arrays.stream(components).forEach(x -> entity.addComponent(x));
        }
        entity.activate();

        return entity;
    }

    /**
     * Creates an instance of Entity, and activates it and its components.
     *
     * @param name The name of the entity.
     * @param position The position of the entity.
     * @param components The components that will be added to this entity.
     * @return The entity.
     */
    public static Entity create(String name, Vector3f position, ComponentBase... components) throws DuplicateComponentTypeException {
        try {
            Entity entity = new Entity(name, position);
            if (components != null) {
                Arrays.stream(components).forEach(x -> entity.addComponent(x));
            }
            entity.activate();

            return entity;
        } catch (DuplicateComponentTypeException ex) {
            throw ex;
        }
    }

    //</editor-fold>
}
