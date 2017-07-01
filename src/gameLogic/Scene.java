/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic;

import gameLogic.terrain.Terrain;
import java.util.Iterator;
import java.util.Map;

/**
 * An instance of this class represents a scene in 3D containing terrains and
 * entities. 
 *
 * @author Blackened
 */
public class Scene {

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

}
