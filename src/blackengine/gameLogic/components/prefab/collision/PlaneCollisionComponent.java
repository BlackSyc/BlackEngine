/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.collision;

import blackengine.gameLogic.components.prefab.collision.base.CollisionChecker;
import blackengine.gameLogic.components.prefab.collision.base.CollisionComponent;


/**
 *
 * @author Blackened
 */
public class PlaneCollisionComponent extends CollisionComponent{

    public PlaneCollisionComponent(String name) {
        super(name);
    }

    @Override
    public boolean isCollidingWith(BoxCollisionComponent bcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCollidingWith(SphereCollisionComponent scc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCollidingWith(PlaneCollisionComponent pcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCollidingWith(MeshCollisionComponent mcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean dispatchCollisionCheck(CollisionChecker cm) {
        return cm.isCollidingWith(this);
    }

    @Override
    public void dispatchCollisionHandling(CollisionComponent cc) {
        cc.handleCollisionWith(this);
    }

    @Override
    public boolean handleCollisionWith(BoxCollisionComponent bcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean handleCollisionWith(MeshCollisionComponent mcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean handleCollisionWith(PlaneCollisionComponent pcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean handleCollisionWith(SphereCollisionComponent scc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
