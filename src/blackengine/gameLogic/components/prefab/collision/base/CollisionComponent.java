/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.collision.base;

import blackengine.gameLogic.Transform;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.toolbox.math.ImmutableVector3;

/**
 *
 * @author Blackened
 */
public abstract class CollisionComponent extends ComponentBase implements CollisionChecker, CollisionCheckDispatcher{
    
    private final String name;
    
    private final Transform transform;

    public String getName() {
        return name;
    }

    public CollisionComponent(String name) {
        this.transform = new Transform(new ImmutableVector3(), new ImmutableVector3(), new ImmutableVector3());
        this.name = name;
    }
    
    @Override
    public void update(){
        //TODO
    }
    
    
    
    
    
}
