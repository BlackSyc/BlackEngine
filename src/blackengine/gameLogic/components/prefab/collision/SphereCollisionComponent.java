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

import blackengine.gameLogic.Transform;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.toolbox.math.ImmutableVector3;
import blackengine.toolbox.math.VectorMath;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public abstract class SphereCollisionComponent extends CollisionComponent {


    public float getRadius() {
        return this.getTransform().getAbsoluteScale().getX();
    }


    public SphereCollisionComponent(float radius) {
        super(new Transform(new ImmutableVector3(), new ImmutableVector3(), new ImmutableVector3(radius, radius, radius)));
    }

    @Override
    public boolean isColliding(SphereCollisionComponent sphereCollisionComponent) {
        ImmutableVector3 otherCenter = sphereCollisionComponent.getCollisionComponentCenter();
        ImmutableVector3 thisCenter = this.getCollisionComponentCenter();
        float minimalDistance = this.getRadius() + sphereCollisionComponent.getRadius();
        return VectorMath.distance(otherCenter, thisCenter) < minimalDistance;
    }
    
    @Override
    public boolean isColliding(BoxCollisionComponent boxCollisionComponent){
        return false;
    }
    
    @Override
    public boolean isColliding(PlaneCollisionComponent planeCollisionComponent){
        return false;
    }

    @Override
    public Class<? extends ComponentBase> getMapping() {
        return CollisionComponent.class;
    }

}
