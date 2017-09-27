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
package blackengine.gameLogic;

import blackengine.toolbox.math.VectorMath;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Transform {

    private Transform parentTransform;
    private final PublishSubject<Transform> transformSubject = PublishSubject.create();
    private Disposable transformSubscription;

    // Both position vectors.
    private Vector3f relativePosition;
    private Vector3f absolutePosition;

    // Both rotation vectors.
    private Vector3f relativeEulerRotation;
    private Vector3f absoluteEulerRotation;

    // Both scale vectores.
    private Vector3f relativeScale;
    private Vector3f absoluteScale;

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public Observable<Transform> getObservable() {
        return this.transformSubject;
    }

    //<editor-fold defaultstate="collapsed" desc="Position">
    public Vector3f getRelativePosition() {
        return relativePosition;
    }

    public Vector3f getAbsolutePosition() {
        return absolutePosition;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Rotation">
    public Vector3f getRelativeEulerRotation() {
        return relativeEulerRotation;
    }

    public Vector3f getAbsoluteEulerRotation() {
        return absoluteEulerRotation;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scale">
    public Vector3f getRelativeScale() {
        return relativeScale;
    }

    public Vector3f getAbsoluteScale() {
        return absoluteScale;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    //<editor-fold defaultstate="collapsed" desc="Position">
    /**
     * Changes this transforms relative position, and therefore its absolute
     * position.
     *
     * @param newRelativePosition
     */
    public void setRelativePosition(Vector3f newRelativePosition) {
        this.relativePosition = new Vector3f(newRelativePosition);
        if (this.parentTransform != null) {
            this.absolutePosition = this.calculateAbsolutePosition(this.parentTransform.getAbsolutePosition(), this.parentTransform.getAbsoluteEulerRotation(), this.relativePosition);
        } else {
            this.absolutePosition = this.relativePosition;
        }
        this.transformSubject.onNext(this);
    }

    /**
     * Changes this transforms absolute position, and therefore its relative
     * position.
     *
     * @param newAbsolutePosition
     */
    public void setAbsolutePosition(Vector3f newAbsolutePosition) {
        this.absolutePosition = new Vector3f(newAbsolutePosition);
        if (this.parentTransform != null) {
            this.relativePosition = this.calculateRelativePosition(this.parentTransform.getAbsolutePosition(), this.parentTransform.getAbsoluteEulerRotation(), this.absolutePosition);
        } else {
            this.relativePosition = this.absolutePosition;
        }
        this.transformSubject.onNext(this);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Rotation">
    /**
     * Changes this transforms relative rotation, and therefore its absolute
     * rotation.
     *
     * @param newRelativeRotation
     */
    public void setRelativeEulerRotation(Vector3f newRelativeRotation) {
        this.relativeEulerRotation = new Vector3f(newRelativeRotation);
        if (this.parentTransform != null) {
            this.absoluteEulerRotation = this.calculateAbsoluteRotation(this.parentTransform.getAbsoluteEulerRotation(), this.relativeEulerRotation);
        } else {
            this.absoluteEulerRotation = this.relativeEulerRotation;
        }
        this.transformSubject.onNext(this);
    }

    /**
     * Changes this transforms absolute rotation, and therefore its relative
     * rotation.
     *
     * @param newAbsoluteRotation
     */
    public void setAbsoluteEulerRotation(Vector3f newAbsoluteRotation) {
        this.absoluteEulerRotation = new Vector3f(newAbsoluteRotation);
        if (this.parentTransform != null) {
            this.relativeEulerRotation = this.calculateRelativeRotation(this.parentTransform.getAbsoluteEulerRotation(), this.absoluteEulerRotation);
        } else {
            this.relativeEulerRotation = this.absoluteEulerRotation;
        }
        this.transformSubject.onNext(this);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scale">
    /**
     * Changes this transforms relative scale, and therefore its absolute scale.
     *
     * @param newRelativeScale
     */
    public void setRelativeScale(Vector3f newRelativeScale) {
        this.relativeScale = new Vector3f(newRelativeScale);

        if (this.parentTransform != null) {
            this.absoluteScale = this.calculateAbsoluteScale(this.parentTransform.getAbsoluteScale(), this.relativeScale);
        } else {
            this.absoluteScale = this.relativeScale;
        }
        this.transformSubject.onNext(this);
    }

    /**
     * Changes this transforms absolute scale, and therefore its relative scale.
     *
     * @param newAbsoluteScale
     */
    public void setAbsoluteScale(Vector3f newAbsoluteScale) {
        this.absoluteScale = new Vector3f(newAbsoluteScale);

        if (this.parentTransform != null) {
            this.relativeScale = this.calculateRelativeScale(this.parentTransform.getAbsoluteScale(), this.absoluteScale);
        } else {
            this.relativeScale = this.absoluteScale;
        }
        this.transformSubject.onNext(this);
    }
    //</editor-fold>
    //</editor-fold>

    /**
     * Default constructor for creating a new instance of Transform.
     *
     * @param position The euler rotation in degrees.
     * @param eulerRotation
     * @param scale
     */
    public Transform(Vector3f position, Vector3f eulerRotation, Vector3f scale) {
        this.relativePosition = position;
        this.absolutePosition = position;

        this.relativeEulerRotation = eulerRotation;
        this.absoluteEulerRotation = eulerRotation;

        this.relativeScale = scale;
        this.absoluteScale = scale;
    }

    public void destroy() {
        this.stopListening();
        this.transformSubject.onComplete();
    }

    /**
     * Disposes of any subscriptions to previous parents, and subscribes to the
     * new parent transform.
     *
     * @param transform
     */
    public void listenTo(Transform transform) {

        this.stopListening();

        this.parentTransform = transform;

        // set the absolute properties for this transform.
        this.absoluteScale = this.calculateAbsoluteScale(this.parentTransform.getAbsoluteScale(), this.relativeScale);
        this.absoluteEulerRotation = this.calculateAbsoluteRotation(this.parentTransform.getAbsoluteEulerRotation(), this.relativeEulerRotation);
        this.absolutePosition = this.calculateAbsolutePosition(this.parentTransform.getAbsolutePosition(), this.parentTransform.getAbsoluteEulerRotation(), this.relativePosition);

        // notify listeners of changes
        this.transformSubject.onNext(this);

        // Subscribe to all changes in the parent transform.
        this.transformSubscription = this.subscribeToParentTransform();
    }

    public void stopListening() {
        this.parentTransform = null;
        if (this.transformSubscription != null) {
            this.transformSubscription.dispose();
            this.transformSubscription = null;
        }

        this.absolutePosition = this.relativePosition;
        this.absoluteEulerRotation = this.relativeEulerRotation;
        this.absoluteScale = this.relativeScale;

        this.transformSubject.onNext(this);
    }

    private Vector3f calculateAbsoluteScale(Vector3f parentsAbsoluteScale, Vector3f ownRelativeScale) {
        return new Vector3f(parentsAbsoluteScale.getX() * ownRelativeScale.getX(),
                parentsAbsoluteScale.getY() * ownRelativeScale.getY(),
                parentsAbsoluteScale.getZ() * ownRelativeScale.getZ());
    }

    private Vector3f calculateRelativeScale(Vector3f parentsAbsoluteScale, Vector3f ownAbsoluteScale) {
        return new Vector3f(ownAbsoluteScale.getX() / parentsAbsoluteScale.getX(),
                ownAbsoluteScale.getY() / parentsAbsoluteScale.getY(),
                ownAbsoluteScale.getZ() / parentsAbsoluteScale.getZ());
    }

    private Vector3f calculateAbsoluteRotation(Vector3f parentsAbsoluteRotation, Vector3f ownRelativeRotation) {
        return Vector3f.add(ownRelativeRotation, parentsAbsoluteRotation, null);
    }

    private Vector3f calculateRelativeRotation(Vector3f parentsAbsoluteRotation, Vector3f ownAbsoluteRotation) {
        return Vector3f.sub(ownAbsoluteRotation, parentsAbsoluteRotation, null);
    }

    /**
     * This is subject to the parents absolute position AND absolute rotation
     * and its own relative position.
     *
     * @param parentsAbsolutePosition
     * @return
     */
    private Vector3f calculateAbsolutePosition(Vector3f parentsAbsolutePosition, Vector3f parentsAbsoluteRotation, Vector3f ownRelativePosition) {
        Vector3f rotatedRelativePosition = VectorMath.rotateEuler(ownRelativePosition, parentsAbsoluteRotation);
        return Vector3f.add(rotatedRelativePosition, parentsAbsolutePosition, null);
    }

    private Vector3f calculateRelativePosition(Vector3f parentsAbsolutePosition, Vector3f parentsAbsoluteRotation, Vector3f ownAbsolutePosition) {
        Vector3f inverseRotatedAbsolutePosition = VectorMath.rotateEuler(ownAbsolutePosition, parentsAbsolutePosition.negate(null));
        return Vector3f.sub(parentsAbsolutePosition, inverseRotatedAbsolutePosition, null);
    }

    private Disposable subscribeToParentTransform() {
        return this.parentTransform.getObservable().subscribe(
                x -> {
                    this.parentTransform = x;
                    this.absoluteScale = this.calculateAbsoluteScale(x.getAbsoluteScale(), this.relativeScale);
                    this.absoluteEulerRotation = this.calculateAbsoluteRotation(x.getAbsoluteEulerRotation(), this.relativeEulerRotation);
                    this.absolutePosition = this.calculateAbsolutePosition(x.getAbsolutePosition(), x.getAbsoluteEulerRotation(), this.relativePosition);
                    this.transformSubject.onNext(this);
                },
                x -> {
                    System.out.println("Error retrieved from parent transform: " + x);
                },
                () -> {
                    this.stopListening();
                });
    }
}
