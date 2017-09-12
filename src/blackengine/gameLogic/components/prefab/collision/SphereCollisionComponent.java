/*
 * The MIT License
 *
 * Copyright 2017 Blackened.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package blackengine.gameLogic.components.prefab.collision;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.GameElement;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.toolbox.math.VectorMath;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public abstract class SphereCollisionComponent extends CollisionComponent {

    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public SphereCollisionComponent(float radius) {
        this.radius = radius;
    }

    @Override
    public Vector3f getCollisionComponentCenter() {
        return Vector3f.add(this.getParent().getTransform().getAbsolutePosition(), this.offset, null);
    }

    @Override
    protected List<Entity> isColliding() {
        List<Entity> collidingEntities = new ArrayList<>();
        GameElement scene = this.getParent().getGameElement();
        Iterator<Entity> entities = scene.getAllEntities();

        while (entities.hasNext()) {

            Entity entity = entities.next();
            if (entity != this.getParent()) {
                if (entity.containsComponent(CollisionComponent.class)) {
                    Vector3f otherCollisionComponentCenter = entity.getComponent(CollisionComponent.class).getCollisionComponentCenter();
                    Vector3f edgePoint = Vector3f.add(this.getCollisionComponentCenter(), this.getRelativeEdgePoint(otherCollisionComponentCenter), null);
                    if (VectorMath.distance(edgePoint, otherCollisionComponentCenter) < this.radius) {
                        collidingEntities.add(entity);
                    }
                }
            }
        }

        return collidingEntities;
    }

    @Override
    protected Vector3f getRelativeEdgePoint(Vector3f otherCollisionComponentCenter) {
        Vector3f directionVector = Vector3f.sub(otherCollisionComponentCenter, this.getCollisionComponentCenter(), null);

        float directionLength = directionVector.length();
        float scaleFactor = this.radius / directionLength;

        Vector edgePointVector = directionVector.scale(scaleFactor);
        return (Vector3f) edgePointVector;

    }

    @Override
    public Class<? extends ComponentBase> getMapping() {
        return CollisionComponent.class;
    }

}
