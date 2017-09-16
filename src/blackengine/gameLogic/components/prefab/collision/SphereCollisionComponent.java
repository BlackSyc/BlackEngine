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
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.toolbox.math.VectorMath;
import java.util.List;
import java.util.stream.Collectors;
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
    protected List<Entity> calculateCollisions() {
        return this.getParent().getGameElement().getAllEntities()
                .filter(x -> !x.equals(this))
                .filter(x -> x.containsComponent(CollisionComponent.class))
                .filter(x -> x.getComponent(CollisionComponent.class).isColliding(this))
                .collect(Collectors.toList());
    }

    @Override
    protected boolean isColliding(SphereCollisionComponent sphereCollisionComponent) {
        Vector3f otherCenter = sphereCollisionComponent.getCollisionComponentCenter();
        Vector3f thisCenter = this.getCollisionComponentCenter();
        float minimalDistance = this.getRadius() + sphereCollisionComponent.getRadius();
        return VectorMath.distance(otherCenter, thisCenter) < minimalDistance;
    }
    
    @Override
    protected boolean isColliding(BoxCollisionComponent boxCollisionComponent){
        return false;
    }
    
    @Override
    protected boolean isColliding(PlaneCollisionComponent planeCollisionComponent){
        return false;
    }

    @Override
    public Class<? extends ComponentBase> getMapping() {
        return CollisionComponent.class;
    }

}
