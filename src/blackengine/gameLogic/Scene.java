/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

import blackengine.gameLogic.terrain.Terrain;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * An instance of this class represents a scene in 3D containing terrains and
 * entities.
 *
 * #Tested
 *
 * @author Blackened
 */
public class Scene {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * The name of the scene.
     */
    private String name;

    /**
     * All terrains that are present in the scene, mapped to their name.
     */
    private Map<String, Terrain> terrains;

    /**
     * All entities that are present in the scene, mapped to their name.
     */
    private Map<String, Entity> entities;

    /**
     * Whether this instance is flagged for destruction or not.
     */
    private boolean destroyed = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Getter for the name of this scene.
     *
     * @return A String representing the name of this scene.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the whether the scene is flagged for destruction or not.
     *
     * @return True if this scene is flagged for destruction, false otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of scene.
     *
     * @param name The name of the new scene.
     */
    public Scene(String name) {
        this.name = name;
        this.entities = new HashMap<>();
        this.terrains = new HashMap<>();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Verifies whether an entity with the specified name is present in this
     * scene.
     *
     * @param name The name which will be checked for presence in this scene.
     * @return True if an entity with the specified name is present, false
     * otherwise.
     */
    public boolean containsEntity(String name) {
        return this.entities.containsKey(name);
    }

    /**
     * Retrieves a specific entity by its name from this scene.
     *
     * @param name The name of the entity to be retrieved.
     * @return The entity with the specified name, or null if none was present
     * in the scene.
     */
    public Entity getEntity(String name) {
        return this.entities.get(name);
    }

    /**
     * Adds an entity to this scene. If one with the same name was already
     * present, that one will be destroyed and replaced with the new one.
     *
     * @param entity The entity that will be added to this scene.
     */
    public void addEntity(Entity entity) {
        if (this.containsEntity(entity.getName())) {
            this.destroyEntity(entity.getName());
        }
        this.entities.put(entity.getName(), entity);
    }

    /**
     * Detaches an entity specified by its name from this scene and returns it.
     *
     * @param name The name of the entity that is to be detached.
     * @return An entity with the specified name if it was present in the scene,
     * null otherwise.
     */
    public Entity detachEntity(String name) {
        Entity entity = this.getEntity(name);
        this.entities.remove(name);
        return entity;
    }

    /**
     * Destroys and remove the entity specified by its name from this scene.
     *
     * @param name The name of the entity that is to be destroyed and removed.
     */
    public void destroyEntity(String name) {
        if (this.entities.containsKey(name)) {
            this.entities.get(name).destroy();
            this.removeEntitiesFlaggedForDestruction();
        }
    }

    /**
     * Calls the update method for all entities present in this scene, and
     * destroys all entities flagged for destruction.
     */
    public void update() {
        this.entities.values().forEach(x -> x.update());

        this.removeEntitiesFlaggedForDestruction();
    }

    /**
     * Destroys this scene and all its entities and flags for destruction.
     */
    public void destroy() {
        this.entities.values().forEach(x -> x.destroy());
        this.removeEntitiesFlaggedForDestruction();
        this.destroyed = true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Removes all entities from this scene that are flagged for destruction.
     */
    private void removeEntitiesFlaggedForDestruction() {
        Iterator<Entity> entityIterator = this.entities.values().iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();
            if (entity.isDestroyed()) {
                this.entities.remove(entity.getName());
            }
        }
    }
    //</editor-fold>

}
