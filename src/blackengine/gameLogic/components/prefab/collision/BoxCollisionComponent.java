/*
 * The MIT License
 *
 * Copyright 2017 clainder.
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
import blackengine.toolbox.math.Maths;

/**
 *
 * @author clainder
 */
public abstract class BoxCollisionComponent extends CollisionComponent {
    
    public BoxCollisionComponent(ImmutableVector3 scale) {
        super(new Transform(new ImmutableVector3(), new ImmutableVector3(), scale));
    }

    /**
     * Returns the absolute X value of the position of the right side of the
     * box.
     *
     * @return the absolute X value of the position of the right side of the
     * box.
     */
    public float getRightBound() {
        return this.getCollisionComponentCenter().getX() + (this.getTransform().getAbsoluteScale().getX() / 2);
    }

    /**
     * Returns the absolute X value of the position of the left side of the box.
     *
     * @return the absolute X value of the position of the left side of the box.
     */
    public float getLeftBound() {
        return this.getCollisionComponentCenter().getX() - (this.getTransform().getAbsoluteScale().getX()/ 2);
    }

    /**
     * Returns the absolute Y value of the position of the top side of the box.
     *
     * @return the absolute Y value of the position of the top side of the box.
     */
    public float getTopBound() {
        return this.getCollisionComponentCenter().getY() + (this.getTransform().getAbsoluteScale().getY() / 2);
    }

    /**
     * Returns the absolute Y value of the position of the bottom side of the
     * box.
     *
     * @return the absolute Y value of the position of the bottom side of the
     * box.
     */
    public float getBottomBound() {
        return this.getCollisionComponentCenter().getY() - (this.getTransform().getAbsoluteScale().getY() / 2);
    }

    /**
     * Returns the absolute Z value of the position of the front side of the
     * box.
     *
     * @return the absolute Z value of the position of the front side of the
     * box.
     */
    public float getFrontBound() {
        return this.getCollisionComponentCenter().getZ() + (this.getTransform().getAbsoluteScale().getZ() / 2);
    }

    /**
     * Returns the absolute Z value of the position of the back side of the box.
     *
     * @return the absolute Z value of the position of the back side of the box.
     */
    public float getBackBound() {
        return this.getCollisionComponentCenter().getZ() - (this.getTransform().getAbsoluteScale().getZ() / 2);
    }

    @Override
    public boolean isColliding(SphereCollisionComponent sphereCollisionComponent) {
        return false;
    }

    /**
     * Checks of this collision component is colliding with the provided
     * boxCollisionComponent.
     *
     * @param boxCollisionComponent The boxCollisionComponent which will be used
     * to check collision with.
     * @return True if a collision is present, false if otherwise.
     */
    @Override
    public boolean isColliding(BoxCollisionComponent boxCollisionComponent) {

        boolean xAxisColliding
                = // Check if the left-most bound of boxCollisionComponent is between this left bound and right bound
                Maths.inRange(boxCollisionComponent.getLeftBound(), this.getLeftBound(), this.getRightBound())
                || // Check if the right-most bound of boxCollisionComponent is between this left bound and right bound
                Maths.inRange(boxCollisionComponent.getRightBound(), this.getLeftBound(), this.getRightBound())
                || // Check if both x bounds of boxCollisionComponent are on either side of this y bounds
                (boxCollisionComponent.getLeftBound() < this.getLeftBound() && boxCollisionComponent.getRightBound() > this.getRightBound());

        boolean yAxisColliding
                = // Check if the bottom-most bound of boxCollisionComponent is between this bottom bound and top bound
                Maths.inRange(boxCollisionComponent.getBottomBound(), this.getBottomBound(), this.getTopBound())
                || // Check if the top-most bound of boxCollisionComponent is between this bottom bound and top bound
                Maths.inRange(boxCollisionComponent.getTopBound(), this.getBottomBound(), this.getTopBound())
                || // Check if both y bounds of boxCollisionComponent are on either side of this y bounds
                (boxCollisionComponent.getBottomBound() < this.getBottomBound() && boxCollisionComponent.getTopBound() > this.getTopBound());

        boolean zAxisColliding
                = // Check if the front-most bound of boxCollisionComponent is between this front bound and back bound
                Maths.inRange(boxCollisionComponent.getFrontBound(), this.getFrontBound(), this.getBackBound())
                || // Check if the back-most bound of boxCollisionComponent is between this front bound and back bound
                Maths.inRange(boxCollisionComponent.getBackBound(), this.getFrontBound(), this.getBackBound())
                || // Check if both z bounds of boxCollisionComponent are on either side of this z bounds
                (boxCollisionComponent.getBackBound() < this.getBackBound() && boxCollisionComponent.getFrontBound() > this.getFrontBound());

        return xAxisColliding && yAxisColliding && zAxisColliding;
    }

    @Override
    public boolean isColliding(PlaneCollisionComponent planeCollisionComponent) {
        return false;
    }

    @Override
    public Class<? extends ComponentBase> getMapping() {
        return CollisionComponent.class;
    }

}
