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
public class SphereCollisionComponent extends CollisionComponent{

    public SphereCollisionComponent(String name) {
        super(name);
    }

    @Override
    public boolean isCollidingWith(BoxCollisionComponent bcc) {
        System.out.println("Checked if this scc (" + super.getName() + ") was colliding with other bcc (" + bcc.getName() + ").");
        return true;
    }

    @Override
    public boolean isCollidingWith(SphereCollisionComponent scc) {
        System.out.println("Checked if this scc (" + super.getName() + ") was colliding with other scc (" + scc.getName() + ").");
        return true;
    }

    @Override
    public boolean isCollidingWith(PlaneCollisionComponent pcc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCollidingWith(MeshCollisionComponent mcc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean dispatchCollisionCheck(CollisionChecker cm) {
        return cm.isCollidingWith(this);
    }
}
