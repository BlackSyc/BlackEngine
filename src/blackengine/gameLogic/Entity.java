/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

import blackengine.gameLogic.components.base.ComponentBase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;
import blackengine.gameLogic.components.prefab.ComponentFabricator;

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
    /**
     * The name of this particular entity object.
     */
    private final String name;

    /**
     * The parent of this entity.
     */
    private Entity parent;

    /**
     * Whether this instance is flagged for destruction or not.
     */
    private boolean destroyed = false;

    /**
     * A map of all child entities mapped to their name.
     */
    private final Map<String, Entity> children;

    /**
     * The position of this entity in 3D space.
     */
    private Vector3f position;

    /**
     * The rotation of this entity.
     */
    private Vector3f rotation;

    /**
     * All components belonging to this entity, mapped to their mapping class.
     */
    private final Map<Class<? extends ComponentBase>, ComponentBase> components;

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

    /**
     * Getter for the parent of this entity.
     *
     * @return The parent of this entity, or null if none is specified.
     */
    public Entity getParent() {
        return parent;
    }

    /**
     * Getter for whether the entity is flagged for destruction or not.
     *
     * @return True if this entity was flagged for destruction, false otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Getter for the relative 3D position of this entity.
     *
     * @return A new instance of Vector3f with the relative 3D position of this
     * entity.
     */
    public Vector3f getRelativePosition() {
        return new Vector3f(position);
    }

    /**
     * Getter for the absolute 3D position of this entity.
     *
     * @return A new instance of Vector3f with the absolute 3D position of this
     * entity.
     */
    public Vector3f getAbsolutePosition() {
        if (this.getParent() != null) {
            return new Vector3f(
                    this.position.x + this.getParent().getAbsolutePosition().x,
                    this.position.y + this.getParent().getAbsolutePosition().y,
                    this.position.z + this.getParent().getAbsolutePosition().z);
        }
        return new Vector3f(this.position);
    }

    /**
     * Getter for the rotation of this entity.
     *
     * @return An instance of Vector3f with the euler rotation of this entity.
     */
    public Vector3f getRotation() {
        return rotation;
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
     * Setter for the 3D position of this entity.
     *
     * @param position An instance of Vector3f containing the new position for
     * this entity in 3D space.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Setter for the rotation of this entity.
     *
     * @param rotation An instance of Vector3f containing the new euler rotation
     * for this entity.
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of the Entity class.
     *
     * @param name The name of this particular entity.
     * @param position The position of this particular entity in 3D space.
     * @param rotation The rotation of this particular entity.
     */
    public Entity(String name, Vector3f position, Vector3f rotation) {
        this.name = name;
        this.position = position;
        this.rotation = rotation;
        this.components = new HashMap<>();
        this.children = new HashMap<>();
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
     * present, that one will be destroyed and replaced with the new one.
     *
     * @param child An entity that will be added to this entity as a child.
     */
    public void addChild(Entity child) {
        if (this.containsChild(child.getName())) {
            this.destroyChild(child.getName());
        }
        this.children.put(child.getName(), child);
        child.setParent(this);
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
     * Adds a component to this entity if a component with this mapping was not
     * yet present. If one was already present, the existing one will be
     * destroyed and replaced.
     *
     * @param component The component to be added to this entity.
     */
    public void addComponent(ComponentBase component) {
        if (this.containsComponent(component.getMapping())) {
            this.getComponent(component.getMapping()).destroy();
        }
        component.setParent(this);
        this.components.put(component.getMapping(), component);
    }

    /**
     * Lets the fabricator create a component and adds it to this entity using
     * the addComponent(ComponentBase component) method.
     *
     * @param fabricator The fabricator needed to construct the component.
     */
    public void addComponent(ComponentFabricator<? extends ComponentBase> fabricator) {
        ComponentBase newComponent = fabricator.create();
        this.addComponent(newComponent);
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
     * Updates all children entities and components. Removes all components and
     * children that are flagged for destruction.
     */
    public void update() {
        // Update all components that are present in the order presented by the
        // order iterator. Removes the components if they are flagged for 
        // destruction.
        Iterator<Class<? extends ComponentBase>> iter = ComponentEngine.getComponentIterator();

        while (iter.hasNext()) {
            Class<? extends ComponentBase> componentClass = iter.next();
            if (this.components.containsKey(componentClass)) {
                ComponentBase currentComponent = this.components.get(componentClass);
                currentComponent.update();
                if (currentComponent.isDestroyed()) {
                    this.components.remove(currentComponent.getMapping());
                }
            }
        }

        // Update all children.
        this.children.values().forEach(x -> x.update());

        // Remove all children that are flagged for destruction.
        this.removeChildrenFlaggedForDestruction();
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
        Iterator<ComponentBase> componentIterator = this.components.values().iterator();
        while (componentIterator.hasNext()) {
            ComponentBase component = componentIterator.next();
            if (component.isDestroyed()) {
                this.components.remove(component.getMapping());
            }
        }
    }

    /**
     * Removes all children from this entity that are flagged for destruction.
     */
    private void removeChildrenFlaggedForDestruction() {
        Iterator<Entity> childrenIterator = this.children.values().iterator();
        while (childrenIterator.hasNext()) {
            Entity child = childrenIterator.next();
            if (child.isDestroyed()) {
                this.children.remove(child.getName());
            }
        }
    }

    //</editor-fold>
}
