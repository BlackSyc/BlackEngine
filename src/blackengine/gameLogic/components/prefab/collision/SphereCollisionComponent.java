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
public class SphereCollisionComponent extends CollisionComponent {

    private float radius;

    public void setRadius(float radius) {
        this.radius = radius;
        super.getTransform().setAbsoluteScale(new ImmutableVector3(radius, radius, radius));
    }

    public float getRadius() {
        return radius;
    }

    public SphereCollisionComponent(float radius) {
        super(new Transform(new ImmutableVector3(),
                new ImmutableVector3(),
                new ImmutableVector3(radius, radius, radius)));
        this.radius = radius;
    }

    @Override
    public final boolean isCollidingWith(BoxCollisionComponent bcc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final boolean isCollidingWith(SphereCollisionComponent scc) {
        ImmutableVector3 otherPosition = scc.getTransform().getAbsolutePosition();
        float distance = this.getTransform().getAbsolutePosition().distanceTo(otherPosition);
        return distance < this.getRadius() + scc.getRadius();
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
//        ImmutableVector3 otherPosition = scc.getTransform().getAbsolutePosition();
//        float requiredDistance = this.getRadius() + scc.getRadius();
//        float actualDistance = this.getTransform().getAbsolutePosition().distanceTo(otherPosition);
//        float absoluteDistanceToMove = requiredDistance - actualDistance;
//        ImmutableVector3 directionToMove = otherPosition.subtract(this.getTransform().getAbsolutePosition()).normalize();

        //NO WEIGHT YET
        if (scc.hasHandledCollisionWith(this)) {
            return;
        } else {
            ImmutableVector3 otherPosition = scc.getTransform().getAbsolutePosition();
            float requiredDistance = this.getRadius() + scc.getRadius();
            float actualDistance = this.getTransform().getAbsolutePosition().distanceTo(otherPosition);
            float absoluteDistanceToMove = requiredDistance - actualDistance;
            ImmutableVector3 directionToMove = this.getTransform().getAbsolutePosition().subtract(otherPosition).normalize();

            // TODO: add weight into absoluteDistanceToMove
            ImmutableVector3 translation = directionToMove.multiplyBy(absoluteDistanceToMove);
            
            ImmutableVector3 originalPosition = this.getParent().getTransform().getRelativePosition();
            this.getParent().getTransform().setRelativePosition(originalPosition.add(translation));

        }

    }

    @Override
    public boolean dispatchCollisionCheck(CollisionChecker cm) {
        return cm.isCollidingWith(this);
    }

    @Override
    public final void dispatchCollisionHandling(CollisionComponent cc) {
        cc.handleCollisionWith(this);
    }
}
