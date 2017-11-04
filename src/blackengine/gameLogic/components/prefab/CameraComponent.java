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
import blackengine.rendering.CameraFrameBuffer;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.pipeline.CameraSettings;
import blackengine.rendering.pipeline.Pipeline;
import blackengine.rendering.pipeline.Resolution;
import blackengine.toolbox.math.ImmutableVector3;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import org.lwjgl.opengl.GL11;
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
 * the {@link blackengine.rendering.RenderEngine RenderEngine}, it will now be
 * set to null.
 *
 * @author Blackened
 */
public class CameraComponent extends ComponentBase implements Camera {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private final String identifier;

    private Disposable parentTransformSubscription;

    private final Pipeline pipeline;

    private final CameraFrameBuffer target;

    private final BehaviorSubject<CameraSettings> settings;

    private final Disposable settingsSubscription;

    private final Resolution resolution;

    private final float priority;

    /**
     * The view matrix of this camera.
     */
    protected Matrix4f viewMatrix = new Matrix4f();

    private Matrix4f projectionMatrix = new Matrix4f();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getIdentifier() {
        return identifier;
    }

    public float getPriority() {
        return priority;
    }

    public Pipeline getPipeline() {
        return this.pipeline;
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

    public CameraFrameBuffer getFrameBuffer() {
        return this.target;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    private CameraComponent(String identifier, Resolution resolution, CameraSettings settings, float priority) {
        this.identifier = identifier;
        this.pipeline = new Pipeline();
        this.resolution = resolution;
        this.target = new CameraFrameBuffer(resolution, new ImmutableVector3(1, 1, 1));
        this.priority = priority;
        this.settings = BehaviorSubject.createDefault(settings);
        this.settingsSubscription = this.settings.subscribe(x -> this.updateProjectionMatrix());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    public void bindFrameBuffer(FrameBufferObject fbo) {
        this.target.unbind();
        fbo.bind();
    }

    public void unbindFrameBuffer(FrameBufferObject fbo) {
        fbo.unbind();
        this.target.bind();
    }

    public void render() {
        this.target.bind();
        this.pipeline.stream().forEach(x -> {
            x.render(this);
        });
        this.target.unbind();
        GL11.glViewport(0, 0, RenderEngine.getInstance().getFrameResolution().getWidth(), RenderEngine.getInstance().getFrameResolution().getHeight());
        this.target.stopFrame();
    }

    /**
     * Deactivates this camera component by setting the main camera in the
     * {@link blackengine.rendering.RenderEngine RenderEngine} to null if and
     * only if this camera component was the main camera. Also sets this
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
    public void destroy() {
        this.parentTransformSubscription.dispose();
        this.settingsSubscription.dispose();
        RenderEngine.getInstance().removeCamera(this);
        this.target.destroy();
        super.destroy();
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
        this.updateViewMatrix(new Transform());
        this.updateProjectionMatrix();
        if (!this.target.isValid()) {
            this.target.createTextureAttachment(this.resolution);
            this.target.createDepthTextureAttachment(this.resolution);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    private void onParentTransformChanged(Transform parentTransform) {
        this.updateViewMatrix(parentTransform);
    }

    private void updateViewMatrix(Transform transform) {
        this.viewMatrix = new Matrix4f();
        this.viewMatrix.setIdentity();

        // Pitch
        Matrix4f.rotate((float) Math.toRadians(-transform.getAbsoluteEulerRotation().getX()), new ImmutableVector3(1, 0, 0).mutable(), this.viewMatrix, this.viewMatrix);

        // Yaw
        Matrix4f.rotate((float) Math.toRadians(-transform.getAbsoluteEulerRotation().getY()), new ImmutableVector3(0, 1, 0).mutable(), this.viewMatrix, this.viewMatrix);

        // Roll
        Matrix4f.rotate((float) Math.toRadians(-transform.getAbsoluteEulerRotation().getZ()), new ImmutableVector3(0, 0, 1).mutable(), this.viewMatrix, this.viewMatrix);

        ImmutableVector3 negativeCameraPos = transform.getAbsolutePosition().negate();
        Matrix4f.translate(negativeCameraPos.mutable(), this.viewMatrix, this.viewMatrix);
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
    //</editor-fold>

    public static CameraComponent create(String identifier, Resolution resolution, CameraSettings settings, float priority) {
        CameraComponent cameraComponent = new CameraComponent(identifier, resolution, settings, priority);
        RenderEngine.getInstance().addCamera(cameraComponent);
        return cameraComponent;
    }

    public static CameraComponent create(String identifier, Resolution resolution, CameraSettings settings) {
        CameraComponent cameraComponent = new CameraComponent(identifier, resolution, settings, 1.0f);
        RenderEngine.getInstance().addCamera(cameraComponent);
        return cameraComponent;
    }
}
