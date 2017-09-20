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

    // Both position vectors, subject and subscription.
    private Vector3f relativePosition;
    private Vector3f absolutePosition;
    private final PublishSubject<Vector3f> absolutePositionSubject = PublishSubject.create();
    private Disposable positionSubscription;

    // Both rotation vectors, subject and subscription.
    private Vector3f relativeEulerRotation;
    private Vector3f absoluteEulerRotation;
    private final PublishSubject<Vector3f> absoluteEulerRotationSubject = PublishSubject.create();
    private Disposable eulerRotationSubscription;

    // Both scale vectores, subject and subscription.
    private Vector3f relativeScale;
    private Vector3f absoluteScale;
    private final PublishSubject<Vector3f> absoluteScaleSubject = PublishSubject.create();
    private Disposable scaleSubscription;

    //<editor-fold defaultstate="collapsed" desc="Getters">
    //<editor-fold defaultstate="collapsed" desc="Position">
    public Vector3f getRelativePosition() {
        return relativePosition;
    }

    public Vector3f getAbsolutePosition() {
        return absolutePosition;
    }

    public Observable<Vector3f> getAbsolutePositionObservable() {
        return absolutePositionSubject;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Rotation">
    public Vector3f getRelativeEulerRotation() {
        return relativeEulerRotation;
    }

    public Vector3f getAbsoluteEulerRotation() {
        return absoluteEulerRotation;
    }

    public Observable<Vector3f> getAbsoluteEulerRotationObservable() {
        return absoluteEulerRotationSubject;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scale">
    public Vector3f getRelativeScale() {
        return relativeScale;
    }

    public Vector3f getAbsoluteScale() {
        return absoluteScale;
    }

    public Observable<Vector3f> getAbsoluteScaleObservable() {
        return absoluteScaleSubject;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    //<editor-fold defaultstate="collapsed" desc="Position">
    /**
     * Changes this transforms relative position, and therefore its absolute
     * position.
     *
     * @param relativePosition
     */
    public void setRelativePosition(Vector3f relativePosition) {
        Vector3f translation = Vector3f.sub(this.relativePosition, relativePosition, null);
        this.translatePosition(translation);
    }

    /**
     * Changes this transforms absolute position, and therefore its relative
     * position.
     *
     * @param absolutePosition
     */
    public void setAbsolutePosition(Vector3f absolutePosition) {
        Vector3f translation = Vector3f.sub(this.absolutePosition, absolutePosition, null);
        this.translatePosition(translation);
    }

    /**
     * Translates this transforms absolute and relative position, and notifies
     * the subscribers of the change in the absolute position.
     *
     * @param translation
     */
    private void translatePosition(Vector3f translation) {
        this.relativePosition = this.relativePosition.translate(translation.getX(), translation.getY(), translation.getZ());
        this.absolutePosition = this.absolutePosition.translate(translation.getX(), translation.getY(), translation.getZ());
        this.absolutePositionSubject.onNext(this.absolutePosition);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Rotation">
    /**
     * Changes this transforms relative rotation, and therefore its absolute
     * rotation.
     *
     * @param relativeEulerRotation
     */
    public void setRelativeEulerRotation(Vector3f relativeEulerRotation) {
        Vector3f translation = Vector3f.sub(this.relativeEulerRotation, relativeEulerRotation, null);
        this.translateRotation(translation);
    }

    /**
     * Changes this transforms absolute rotation, and therefore its relative
     * rotation.
     *
     * @param absoluteEulerRotation
     */
    public void setAbsoluteEulerRotation(Vector3f absoluteEulerRotation) {
        Vector3f translation = Vector3f.sub(this.absoluteEulerRotation, absoluteEulerRotation, null);
        this.translateRotation(translation);
    }

    /**
     * Translates this transforms absolute and relative rotation, and notifies
     * the subscribers of the change in the absolute rotation.
     *
     * @param translation
     */
    private void translateRotation(Vector3f translation) {
        this.relativeEulerRotation = this.relativeEulerRotation.translate(translation.getX(), translation.getY(), translation.getZ());
        this.absoluteEulerRotation = this.absoluteEulerRotation.translate(translation.getX(), translation.getY(), translation.getZ());
        this.absoluteEulerRotationSubject.onNext(this.absoluteEulerRotation);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scale">
    /**
     * Changes this transforms relative scale, and therefore its absolute scale.
     *
     * @param relativeScale
     */
    public void setRelativeScale(Vector3f relativeScale) {
        
        Vector3f parentsAbsoluteScale = this.parentTransform != null ? this.parentTransform.getAbsoluteScale() : new Vector3f(1,1,1);
        this.relativeScale = new Vector3f(relativeScale);
        
        this.absoluteScale = new Vector3f(parentsAbsoluteScale.getX() * this.relativeScale.getX(), 
                parentsAbsoluteScale.getY() * this.relativeScale.getY(), 
                parentsAbsoluteScale.getZ() * this.relativeScale.getZ());
        this.absoluteScaleSubject.onNext(this.absoluteScale);
    }

    /**
     * Changes this transforms absolute scale, and therefore its relative scale.
     *
     * @param absoluteScale
     */
    public void setAbsoluteScale(Vector3f absoluteScale) {
        
        Vector3f parentsAbsoluteScale = this.parentTransform != null ? this.parentTransform.getAbsoluteScale() : new Vector3f(1,1,1);
        this.absoluteScale = new Vector3f(absoluteScale);
        
        this.relativeScale = new Vector3f(this.absoluteScale.getX() / parentsAbsoluteScale.getX(), 
                this.absoluteScale.getY() / parentsAbsoluteScale.getY(), 
                this.absoluteScale.getZ() / parentsAbsoluteScale.getZ());
        this.absoluteScaleSubject.onNext(this.absoluteScale);
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
        this.absoluteEulerRotationSubject.onComplete();
        this.absolutePositionSubject.onComplete();
        this.absoluteScaleSubject.onComplete();
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
        this.absolutePosition = Vector3f.add(this.parentTransform.getAbsolutePosition(), this.relativePosition, null);
        this.absoluteEulerRotation = Vector3f.add(this.parentTransform.getAbsoluteEulerRotation(), this.relativeEulerRotation, null);
        this.absoluteScale = new Vector3f(this.parentTransform.getAbsoluteScale().getX() * this.relativeScale.getX(), 
                this.parentTransform.getAbsoluteScale().getY() * this.relativeScale.getY(), 
                this.parentTransform.getAbsoluteScale().getZ() * this.relativeScale.getZ());

        // notify listeners of changes
        this.absolutePositionSubject.onNext(this.absolutePosition);
        this.absoluteEulerRotationSubject.onNext(this.absoluteEulerRotation);
        this.absoluteScaleSubject.onNext(this.absoluteScale);

        // Subscribe to all changes in the absolute position of the parent transform.
        this.positionSubscription = this.parentTransform.getAbsolutePositionObservable()
                .subscribe(x -> {
                    this.absolutePosition = Vector3f.add(x, this.relativePosition, null);
                    this.absolutePositionSubject.onNext(this.absolutePosition);
                },
                        x -> System.out.println(x),
                        () -> {
                            this.absolutePosition = this.relativePosition;
                            this.absolutePositionSubject.onNext(this.absolutePosition);
                        });

        this.eulerRotationSubscription = this.parentTransform.getAbsoluteEulerRotationObservable()
                .subscribe(x -> {
                    this.absoluteEulerRotation = Vector3f.add(x, this.relativeEulerRotation, null);
                    this.absoluteEulerRotationSubject.onNext(this.absoluteEulerRotation);
                },
                        x -> System.out.println(x),
                        () -> {
                            this.absoluteEulerRotation = this.relativeEulerRotation;
                            this.absoluteEulerRotationSubject.onNext(this.absoluteEulerRotation);
                        });

        this.scaleSubscription = this.parentTransform.getAbsoluteScaleObservable()
                .subscribe(x -> {
                    this.absoluteScale = new Vector3f(this.relativeScale.getX() * x.getX(), this.relativeScale.getY() * x.getY(), this.relativeScale.getZ() * x.getZ());
                    this.absoluteScaleSubject.onNext(this.absoluteScale);
                },
                        x -> System.out.println(x),
                        () -> {
                            this.absoluteScale = this.relativeScale;
                            this.absoluteScaleSubject.onNext(this.absoluteScale);
                        });
    }

    public void stopListening() {
        this.parentTransform = null;
        if (this.positionSubscription != null) {
            this.positionSubscription.dispose();
            this.positionSubscription = null;
        }
        if (this.eulerRotationSubscription != null) {
            this.eulerRotationSubscription.dispose();
            this.eulerRotationSubscription = null;
        }
        if (this.scaleSubscription != null) {
            this.scaleSubscription.dispose();
            this.scaleSubscription = null;
        }

        this.absolutePosition = this.relativePosition;
        this.absoluteEulerRotation = this.relativeEulerRotation;
        this.absoluteScale = this.relativeScale;

        this.absolutePositionSubject.onNext(this.absolutePosition);
        this.absoluteEulerRotationSubject.onNext(this.absoluteEulerRotation);
        this.absoluteScaleSubject.onNext(this.absoluteScale);

    }
}
