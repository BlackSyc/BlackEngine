/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.collision.base;

import blackengine.gameLogic.components.prefab.collision.BoxCollisionComponent;
import blackengine.gameLogic.components.prefab.collision.MeshCollisionComponent;
import blackengine.gameLogic.components.prefab.collision.PlaneCollisionComponent;
import blackengine.gameLogic.components.prefab.collision.SphereCollisionComponent;


/**
 *
 * @author Blackened
 */
public interface CollisionChecker{
    
    public boolean isCollidingWith(BoxCollisionComponent bcc);
    
    public boolean isCollidingWith(SphereCollisionComponent scc);
    
    public boolean isCollidingWith(PlaneCollisionComponent pcc);
    
    public boolean isCollidingWith(MeshCollisionComponent mcc);
    
}
