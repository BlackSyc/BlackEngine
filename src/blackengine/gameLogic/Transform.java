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

import blackengine.toolbox.math.ImmutableVector3;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Transform {

    private Transform parentTransform;
    private PublishSubject<Transform> transformSubject = PublishSubject.create();
    private Disposable transformSubscription;

    // Both position vectors.
    private ImmutableVector3 relativePosition;
    private ImmutableVector3 absolutePosition;

    // Both rotation vectors.
    private ImmutableVector3 relativeEulerRotation;
    private ImmutableVector3 absoluteEulerRotation;

    // Both scale vectores.
    private ImmutableVector3 relativeScale;
    private ImmutableVector3 absoluteScale;

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public Observable<Transform> getObservable() {
        return this.transformSubject;
    }

    //<editor-fold defaultstate="collapsed" desc="Position">
    public ImmutableVector3 getRelativePosition() {
        return relativePosition;
    }

    public ImmutableVector3 getAbsolutePosition() {
        return absolutePosition;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Rotation">
    public ImmutableVector3 getRelativeEulerRotation() {
        return relativeEulerRotation;
    }

    public ImmutableVector3 getAbsoluteEulerRotation() {
        return absoluteEulerRotation;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scale">
    public ImmutableVector3 getRelativeScale() {
        return relativeScale;
    }

    public ImmutableVector3 getAbsoluteScale() {
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
    public void setRelativePosition(ImmutableVector3 newRelativePosition) {
        this.relativePosition = newRelativePosition;
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
    public void setAbsolutePosition(ImmutableVector3 newAbsolutePosition) {
        this.absolutePosition = newAbsolutePosition;
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
    public void setRelativeEulerRotation(ImmutableVector3 newRelativeRotation) {
        this.relativeEulerRotation = newRelativeRotation;
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
    public void setAbsoluteEulerRotation(ImmutableVector3 newAbsoluteRotation) {
        this.absoluteEulerRotation = newAbsoluteRotation;
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
    public void setRelativeScale(ImmutableVector3 newRelativeScale) {
        this.relativeScale = newRelativeScale;

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
    public void setAbsoluteScale(ImmutableVector3 newAbsoluteScale) {
        this.absoluteScale = newAbsoluteScale;

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
    public Transform(ImmutableVector3 position, ImmutableVector3 eulerRotation, ImmutableVector3 scale) {
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
    
    public Matrix4f createTransformationMatrix(){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(this.getAbsolutePosition().mutable(), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(this.getAbsoluteEulerRotation().getX()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(this.getAbsoluteEulerRotation().getY()), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(this.getAbsoluteEulerRotation().getZ()), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(this.getAbsoluteScale().mutable(), matrix, matrix);
        return matrix;
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

    private ImmutableVector3 calculateAbsoluteScale(ImmutableVector3 parentsAbsoluteScale, ImmutableVector3 ownRelativeScale) {
        return parentsAbsoluteScale.multiplyBy(ownRelativeScale);
    }

    private ImmutableVector3 calculateRelativeScale(ImmutableVector3 parentsAbsoluteScale, ImmutableVector3 ownAbsoluteScale) {
        return ownAbsoluteScale.divideBy(parentsAbsoluteScale);
    }

    private ImmutableVector3 calculateAbsoluteRotation(ImmutableVector3 parentsAbsoluteRotation, ImmutableVector3 ownRelativeRotation) {
        return ownRelativeRotation.add(parentsAbsoluteRotation);
    }

    private ImmutableVector3 calculateRelativeRotation(ImmutableVector3 parentsAbsoluteRotation, ImmutableVector3 ownAbsoluteRotation) {
        return ownAbsoluteRotation.subtract(parentsAbsoluteRotation);
    }

    /**
     * This is subject to the parents absolute position AND absolute rotation
     * and its own relative position.
     *
     * @param parentsAbsolutePosition
     * @return
     */
    private ImmutableVector3 calculateAbsolutePosition(ImmutableVector3 parentsAbsolutePosition, ImmutableVector3 parentsAbsoluteRotation, ImmutableVector3 ownRelativePosition) {
        ImmutableVector3 rotatedRelativePosition = ownRelativePosition.rotate(parentsAbsoluteRotation);
        ImmutableVector3 result = rotatedRelativePosition.add(parentsAbsolutePosition);
        return result;
    }

    private ImmutableVector3 calculateRelativePosition(ImmutableVector3 parentsAbsolutePosition, ImmutableVector3 parentsAbsoluteRotation, ImmutableVector3 ownAbsolutePosition) {
        ImmutableVector3 inverseRotatedAbsolutePosition = ownAbsolutePosition.rotate(parentsAbsolutePosition.negate());
        ImmutableVector3 result = parentsAbsolutePosition.subtract(inverseRotatedAbsolutePosition);
        return result;
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
