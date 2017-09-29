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
public class MeshCollisionComponent extends CollisionComponent {

    public MeshCollisionComponent() {
        super();
    }

    @Override
    public final boolean isCollidingWith(BoxCollisionComponent bcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final boolean isCollidingWith(SphereCollisionComponent scc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final boolean isCollidingWith(PlaneCollisionComponent pcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final boolean isCollidingWith(MeshCollisionComponent mcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleCollisionWith(BoxCollisionComponent bcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleCollisionWith(MeshCollisionComponent mcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleCollisionWith(PlaneCollisionComponent pcc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleCollisionWith(SphereCollisionComponent scc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final boolean dispatchCollisionCheck(CollisionChecker cm) {
        return cm.isCollidingWith(this);
    }

    @Override
    public final void dispatchCollisionHandling(CollisionComponent cc) {
        cc.handleCollisionWith(this);
    }
}
