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
public class Scene extends GameElement {

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
    
    private boolean active = false;
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

    public boolean isActive() {
        return active;
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
        if (entity != null) {
            if (this.containsEntity(entity.getName())) {
                this.destroyEntity(entity.getName());
            }
            this.entities.put(entity.getName(), entity);
        }
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
    @Override
    public void update() {
        this.entities.values().forEach(x -> x.update());

        this.removeEntitiesFlaggedForDestruction();
    }
    
    /**
     * Activates this scene, and all its entities and their components.
     */
    @Override
    public void activate(){
        this.active = true;
        this.entities.values().forEach(x -> {
            x.activate();
        });
    }
    
    @Override
    public void deactivate(){
        this.entities.values().forEach(x -> {
            x.deactivate();
        });
        this.active = false;
    }

    /**
     * Destroys this scene and all its entities and flags for destruction.
     */
    @Override
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
        this.entities.entrySet().removeIf(x -> x.getValue().isDestroyed());
    }
    //</editor-fold>

}
