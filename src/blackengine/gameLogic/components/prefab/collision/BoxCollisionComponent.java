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
import blackengine.toolbox.math.Maths;

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
        // Firstly, transform the sphere's position to the relative space of this box.
        ImmutableVector3 absoluteSpherePosition = scc.getTransform().getAbsolutePosition();
        ImmutableVector3 relativeSpherePosition = absoluteSpherePosition
                .subtract(this.getTransform().getAbsolutePosition())
                .rotate(this.getTransform().getAbsoluteEulerRotation());
        
        // Then, get closest point on edge of box to sphere.
        float x = Maths.clamp(relativeSpherePosition.getX(), this.getRelativeCorner1().getX(), this.getRelativeCorner2().getX());
        float y = Maths.clamp(relativeSpherePosition.getY(), this.getRelativeCorner1().getY(), this.getRelativeCorner2().getY());
        float z = Maths.clamp(relativeSpherePosition.getZ(), this.getRelativeCorner1().getZ(), this.getRelativeCorner2().getZ());
        ImmutableVector3 boxEdgePoint = new ImmutableVector3(x, y, z);
        
        // Lastly, check if that point is within the radius of the sphere.
        float edgePointToSphereCenter = boxEdgePoint.distanceTo(relativeSpherePosition);
        return edgePointToSphereCenter < scc.getRadius();
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
