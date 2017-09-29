/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.collision.base;

import blackengine.gameLogic.Transform;
import blackengine.gameLogic.components.base.ComponentBase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Blackened
 */
public abstract class CollisionComponent extends ComponentBase
        implements CollisionChecker, CollisionCheckDispatcher,
        CollisionHandler, CollisionHandlingDispatcher {

    private final List<CollisionComponent> collidingComponentCache;

    private final Transform transform;
    
    // possibly add momentum later?
    private float weight;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Transform getTransform() {
        return transform;
    }

    @Override
    public void onActivate() {
        this.transform.listenTo(this.getParent().getTransform());
    }

    public CollisionComponent() {
        this(new Transform());
    }
    
    public CollisionComponent(Transform offsetTransform){
        this.transform = offsetTransform;
        this.collidingComponentCache = new ArrayList<>();
        this.weight = 1;
    }
    
    public boolean hasHandledCollisionWith(CollisionComponent cc){
        return !this.collidingComponentCache.contains(cc);
    }

    @Override
    public final void update() {
        this.addCollisionsToCache();
    }

    private void addCollisionsToCache() {
        this.getParent().getGameElement().flattened()
                .filter(x -> x != this.getParent())
                .filter(x -> x.containsComponent(CollisionComponent.class))
                .map(x -> x.getComponent(CollisionComponent.class))
                .filter(x -> !this.collidingComponentCache.contains(x))
                .filter(x -> x.dispatchCollisionCheck(this))
                .forEach(x -> {
                    this.collidingComponentCache.add(x);
                    x.collidingComponentCache.add(this);
                });
    }

    @Override
    public final void lateUpdate() {
        this.collidingComponentCache
                .forEach(x -> x.dispatchCollisionHandling(this));
        this.collidingComponentCache.clear();
    }

    @Override
    public final Class<? extends ComponentBase> getMapping() {
        return CollisionComponent.class;
    }

}
