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

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.toolbox.math.VectorMath;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author clainder
 */
public abstract class BoxCollisionComponent extends CollisionComponent {

    private float width;
    private float height;
    private float depth;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public BoxCollisionComponent(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * Returns the absolute X value of the position of the right side of the box.
     * @return the absolute X value of the position of the right side of the box.
     */
    public float getRightBound() {
        return this.getCollisionComponentCenter().getX() + (this.width / 2);
    }

    /**
     * Returns the absolute X value of the position of the left side of the box.
     * @return the absolute X value of the position of the left side of the box.
     */
    public float getLeftBound() {
        return this.getCollisionComponentCenter().getX() - (this.width / 2);
    }

    /**
     * Returns the absolute Y value of the position of the top side of the box.
     * @return the absolute Y value of the position of the top side of the box.
     */
    public float getTopBound() {
        return this.getCollisionComponentCenter().getY() + (this.height / 2);
    }

    /**
     * Returns the absolute Y value of the position of the bottom side of the box.
     * @return the absolute Y value of the position of the bottom side of the box.
     */
    public float getBottomBound() {
        return this.getCollisionComponentCenter().getY() - (this.height / 2);
    }

    /**
     * Returns the absolute Z value of the position of the front side of the box.
     * @return the absolute Z value of the position of the front side of the box.
     */
    public float getFrontBound() {
        return this.getCollisionComponentCenter().getZ() + (this.depth / 2);
    }

    /**
     * Returns the absolute Z value of the position of the back side of the box.
     * @return the absolute Z value of the position of the back side of the box.
     */
    public float getBackBound() {
        return this.getCollisionComponentCenter().getZ() - (this.depth / 2);
    }

    @Override
    protected List<Entity> calculateCollisions() {
        return this.getParent().getGameElement().getAllEntities()
                .filter(x -> x.containsComponent(CollisionComponent.class))
                .filter(x -> x.getComponent(CollisionComponent.class).isColliding(this))
                .collect(Collectors.toList());
    }

    @Override
    protected boolean isColliding(SphereCollisionComponent sphereCollisionComponent) {
        return false;
    }

    @Override
    protected boolean isColliding(BoxCollisionComponent boxCollisionComponent) {
        Vector3f[] cornerPoints = new Vector3f[] {
            new Vector3f(this.getRightBound(), this.getTopBound(), this.getFrontBound()),
            new Vector3f(this.getLeftBound(), this.getTopBound(), this.getFrontBound()),
            new Vector3f(this.getRightBound(), this.getBottomBound(), this.getFrontBound()),
            new Vector3f(this.getLeftBound(), this.getBottomBound(), this.getFrontBound()),
            new Vector3f(this.getRightBound(), this.getTopBound(), this.getBackBound()),
            new Vector3f(this.getLeftBound(), this.getTopBound(), this.getBackBound()),
            new Vector3f(this.getRightBound(), this.getBottomBound(), this.getBackBound()),
            new Vector3f(this.getLeftBound(), this.getBottomBound(), this.getBackBound())
        };
        
        // Corner point with minimum distance to the center of the other boxcollider
        Vector3f vector = Stream.of(cornerPoints).min((x, y) -> 
                Float.compare(VectorMath.distance(x, boxCollisionComponent.getCollisionComponentCenter()),
                        VectorMath.distance(y, boxCollisionComponent.getCollisionComponentCenter()))).get();

        // Is closest corner point inside other box.
        return vector.getX() < boxCollisionComponent.getRightBound() && 
                vector.getX() > boxCollisionComponent.getLeftBound() && 
                vector.getY() < boxCollisionComponent.getTopBound() && 
                vector.getY() > boxCollisionComponent.getBottomBound() && 
                vector.getZ() < boxCollisionComponent.getFrontBound() && 
                vector.getZ() > boxCollisionComponent.getBackBound();
    }

    @Override
    protected boolean isColliding(PlaneCollisionComponent planeCollisionComponent) {
        return false;
    }

    @Override
    public Class<? extends ComponentBase> getMapping() {
        return CollisionComponent.class;
    }
}
