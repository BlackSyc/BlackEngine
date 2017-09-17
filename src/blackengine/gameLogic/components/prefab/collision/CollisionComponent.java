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
import java.util.List;
import java.util.stream.Collectors;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public abstract class CollisionComponent extends ComponentBase {

    protected Vector3f offset = new Vector3f();

    public Vector3f getOffset() {
        return offset;
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }

    private List<Entity> calculateCollisions() {
        return this.getParent().getGameElement().getAllEntities()
                .filter(x -> !x.equals(this.getParent()))
                .filter(x -> x.containsComponent(CollisionComponent.class))
                .filter(x -> {
                    if (this instanceof SphereCollisionComponent) {
                        return x.getComponent(CollisionComponent.class).isColliding((SphereCollisionComponent) this);
                    } else if (this instanceof BoxCollisionComponent) {
                        return x.getComponent(CollisionComponent.class).isColliding((BoxCollisionComponent) this);
                    } else if (this instanceof PlaneCollisionComponent) {
                        return x.getComponent(CollisionComponent.class).isColliding((PlaneCollisionComponent) this);
                    } else{
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public abstract boolean isColliding(SphereCollisionComponent sphereCollisionComponent);

    public abstract boolean isColliding(BoxCollisionComponent boxCollisionComponent);

    public abstract boolean isColliding(PlaneCollisionComponent planeCollisionComponent);

    protected abstract void onCollision(Entity otherEntity);

    public Vector3f getCollisionComponentCenter() {
        return Vector3f.add(this.getParent().getTransform().getAbsolutePosition(), this.offset, null);
    }

    @Override
    public void update() {
        List<Entity> collidingEntities = this.calculateCollisions();
        if (collidingEntities.size() > 0) {
            collidingEntities.forEach(x -> this.onCollision(x));
        }
    }

}
