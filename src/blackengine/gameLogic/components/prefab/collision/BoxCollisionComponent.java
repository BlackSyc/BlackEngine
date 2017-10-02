/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab.collision;

import blackengine.gameLogic.Transform;
import blackengine.gameLogic.components.prefab.collision.base.CollisionChecker;
import blackengine.gameLogic.components.prefab.collision.base.CollisionComponent;
import blackengine.toolbox.math.ImmutableVector3;

/**
 *
 * @author Blackened
 */
public class BoxCollisionComponent extends CollisionComponent {
    
    public ImmutableVector3 getRelativeCorner1(){
        return this.getTransform().getAbsoluteScale().divideBy(2).negate();
    }
    
    public ImmutableVector3 getRelativeCorner2(){
        return this.getTransform().getAbsoluteScale().divideBy(2);
    }

    public BoxCollisionComponent() {
        super();
    }
    
    public BoxCollisionComponent(Transform offsetTransform){
        super(offsetTransform);
    }

    /**
     * Check if THIS is colliding with bcc.
     *
     * @param bcc
     * @return
     */
    @Override
    public final boolean isCollidingWith(BoxCollisionComponent bcc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final boolean isCollidingWith(SphereCollisionComponent scc) {
        ImmutableVector3 directionVector = scc.getTransform().getAbsolutePosition().subtract(this.getTransform().getAbsolutePosition());
        ImmutableVector3 rotatedDirectionVector = directionVector.rotate(this.getTransform().getAbsoluteEulerRotation());
        
        ImmutableVector3 radiusSubtraction = scc.getTransform().getAbsoluteScale();
        
        ImmutableVector3 immutableDirectionVector = rotatedDirectionVector.subtract(radiusSubtraction);
        
        boolean xAxisOverlapping = immutableDirectionVector.getX() < this.getRelativeCorner2().getX() && immutableDirectionVector.getX() > this.getRelativeCorner1().getX();
        boolean yAxisOverlapping = immutableDirectionVector.getY() < this.getRelativeCorner2().getY() && immutableDirectionVector.getY() > this.getRelativeCorner1().getY();
        boolean zAxisOverlapping = immutableDirectionVector.getZ() < this.getRelativeCorner2().getZ() && immutableDirectionVector.getZ() > this.getRelativeCorner1().getZ();
        
        return xAxisOverlapping && yAxisOverlapping && zAxisOverlapping;
    }

    @Override
    public final boolean isCollidingWith(PlaneCollisionComponent pcc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final boolean isCollidingWith(MeshCollisionComponent mcc) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        this.setColliding(true);
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
