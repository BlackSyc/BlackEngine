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
        this(radius, 1);
    }

    public SphereCollisionComponent(float radius, float weight) {
        super(new Transform(new ImmutableVector3(),
                new ImmutableVector3(),
                new ImmutableVector3(radius, radius, radius)), weight);
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
        if (this.getWeight() > 0) {
            ImmutableVector3 otherPosition = scc.getTransform().getAbsolutePosition();
            float requiredDistance = this.getRadius() + scc.getRadius();
            float actualDistance = this.getTransform().getAbsolutePosition().distanceTo(otherPosition);
            float absoluteDistanceToMove = requiredDistance - actualDistance;
            ImmutableVector3 directionToMove = this.getTransform().getAbsolutePosition().subtract(otherPosition).normalize();

            ImmutableVector3 translation;
            if (scc.hasHandledCollisionWith(this) || scc.getWeight() <= 0) {
                translation = directionToMove.multiplyBy(absoluteDistanceToMove);
            } else {
                float weightedDistanceToMove = (absoluteDistanceToMove / (this.getWeight() + scc.getWeight())) * this.getWeight();
                translation = directionToMove.multiplyBy(weightedDistanceToMove);
            }

            ImmutableVector3 originalPosition = this.getParent().getTransform().getAbsolutePosition();
            this.getParent().getTransform().setAbsolutePosition(originalPosition.add(translation));
        }
        this.setColliding(true);
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
