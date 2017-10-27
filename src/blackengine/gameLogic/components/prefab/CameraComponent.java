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
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.Transform;
import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.openGL.frameBuffer.FrameBufferObject;
import blackengine.rendering.Camera;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.pipeline.CameraSettings;
import blackengine.rendering.pipeline.Pipeline;
import blackengine.rendering.pipeline.Resolution;
import blackengine.toolbox.math.ImmutableVector3;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import org.lwjgl.util.vector.Matrix4f;

/**
 * An implementation of this component can function as a camera that can be used
 * to render from a POV. It is required to register this component to the
 * {@link blackengine.gameLogic.LogicEngine LogicEngine}, as the update method
 * itself handles the creation of the view matrix. In general, this components
 * {@link #update() update()} method should be called after all other
 * components, as the camera position may be dependent on their implementation.
 * This means this component should be registered with a low priority to the
 * {@link blackengine.gameLogic.LogicEngine LogicEngine}.
 *
 * On {@link #activate() activate()}, this implementation of CameraComponent
 * will be set as the main camera from which the POV renderers will render.
 *
 * On {@link #deactivate() deactivate()}, if this camera was the main camera in
 * the {@link blackengine.rendering.RenderEngine RenderEngine}, it will now
 * be set to null.
 *
 * @author Blackened
 */
public class CameraComponent extends ComponentBase implements Camera {

    private Disposable parentTransformSubscription;

    private ImmutableVector3 position = new ImmutableVector3();

    private ImmutableVector3 offset = new ImmutableVector3();
    
    private final Pipeline pipeline;
    
    private final FrameBufferObject target;
    
    private final BehaviorSubject<CameraSettings> settings;
    
    private final Disposable settingsSubscription;
    
    
    private final Resolution resolution;
    
    private final float priority;

    /**
     * The view matrix of this camera.
     */
    protected Matrix4f viewMatrix = new Matrix4f();
    
    private Matrix4f projectionMatrix = new Matrix4f();

    public float getPriority() {
        return priority;
    }

    public ImmutableVector3 getOffset() {
        return offset;
    }

    public void setOffset(ImmutableVector3 offset) {
        this.offset = offset;
    }

    public CameraComponent(Resolution resolution, float priority) {
        this.pipeline = new Pipeline();
        this.resolution = resolution;
        this.target = new FrameBufferObject(resolution.getWidth(), resolution.getHeight());
        this.priority = priority;
        this.settings = BehaviorSubject.createDefault(CameraSettings.createDefault());
        this.settingsSubscription = settings.subscribe(x -> this.updateProjectionMatrix());
        
        // This is fine, as it is only adding this instance to a collection.
        RenderEngine.getInstance().addCamera(this);
    }
    
    public void render(){
        this.pipeline.stream().forEach(x -> {
            x.render(this);
        });
    }
    
    @Override
    public void setParent(Entity parent){
        if(parent != null){
            this.position = this.offset.add(parent.getTransform().getAbsolutePosition());
        }
        super.setParent(parent);
    }

    /**
     * Activates this camera component by setting the main camera in the
     * {@link blackengine.rendering.RenderEngine RenderEngine} to this and
     * setting this active flag to true.
     */
    @Override
    public void onActivate() {
        this.parentTransformSubscription = this.getParent().getTransform().getObservable()
                .subscribe(x -> this.onParentTransformChanged(x));
        this.updateViewMatrix();
        this.updateProjectionMatrix();
        if(!this.target.isValid()){
            this.target.createTextureAttachment(this.resolution.getWidth(), this.resolution.getHeight());
            this.target.createDepthAttachment(this.resolution.getWidth(), this.resolution.getHeight());
        }
    }

    public void onParentTransformChanged(Transform parentTransform) {
        this.updatePosition(parentTransform.getAbsolutePosition());
        this.updateViewMatrix();
    }

    private void updateViewMatrix() {
        this.viewMatrix = new Matrix4f();
        this.viewMatrix.setIdentity();
        
        // Pitch
        Matrix4f.rotate((float) Math.toRadians(-this.getParent().getTransform().getAbsoluteEulerRotation().getX()), new ImmutableVector3(1, 0, 0).mutable(), this.viewMatrix, this.viewMatrix);
        
        // Yaw
        Matrix4f.rotate((float) Math.toRadians(-this.getParent().getTransform().getAbsoluteEulerRotation().getY()), new ImmutableVector3(0, 1, 0).mutable(), this.viewMatrix, this.viewMatrix);
        
        // Roll
        Matrix4f.rotate((float) Math.toRadians(-this.getParent().getTransform().getAbsoluteEulerRotation().getZ()), new ImmutableVector3(0, 0, 1).mutable(), this.viewMatrix, this.viewMatrix);
        
        ImmutableVector3 negativeCameraPos = this.getPosition().negate();
        Matrix4f.translate(negativeCameraPos.mutable(), this.viewMatrix, this.viewMatrix);
    }

    private void updatePosition(ImmutableVector3 parentPosition) {
        this.position = this.offset.add(parentPosition);
    }

    /**
     * Deactivates this camera component by setting the main camera in the
     * {@link blackengine.rendering.RenderEngine RenderEngine} to null if
     * and only if this camera component was the main camera. Also sets this
     * components active flag to false.
     */
    @Override
    public void onDeactivate() {
        if (this.isActive()) {
            this.parentTransformSubscription.dispose();
            this.parentTransformSubscription = null;
        }
    }
    
    @Override
    public void destroy(){
        this.parentTransformSubscription.dispose();
        this.settingsSubscription.dispose();
        RenderEngine.getInstance().removeCamera(this);
        super.destroy();
    }

    /**
     * Retrieves the view matrix from this camera.
     *
     * @return An instance of {@link org.lwjgl.util.vector.Matrix4f Matrix4f} as
     * a new view matrix.
     */
    @Override
    public Matrix4f getViewMatrix() {
        return this.viewMatrix;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    @Override
    public ImmutableVector3 getPosition() {
        return this.position;
    }
    
        /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     *
     * @param fieldOfView
     * @param nearPlane
     * @param farPlane
     */
    private void updateProjectionMatrix() {
        float aspectRatio = this.resolution.getWidth() / this.resolution.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(this.settings.getValue().getFieldOfView() / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = this.settings.getValue().getFarClippingPlane() - this.settings.getValue().getNearClippingPlane();

        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
        this.projectionMatrix.m22 = -((this.settings.getValue().getFarClippingPlane() + this.settings.getValue().getNearClippingPlane()) / frustum_length);
        this.projectionMatrix.m23 = -1;
        this.projectionMatrix.m32 = -((2 * this.settings.getValue().getNearClippingPlane() * this.settings.getValue().getFarClippingPlane()) / frustum_length);
        this.projectionMatrix.m33 = 0;
    }
    
}
