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

import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.rendering.Camera;
import blackengine.rendering.RenderEngine;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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
 * the {@link blackengine.rendering.MasterRenderer MasterRenderer}, it will now
 * be set to null.
 *
 * @author Blackened
 */
public class CameraComponent extends ComponentBase implements Camera {

    /**
     * The pitch of this camera in degrees.
     */
    private double pitch = 0;

    /**
     * The yaw of this camera in degrees.
     */
    private double yaw = 0;

    /**
     * The roll of this camera in degrees.
     */
    private double roll = 0;

    /**
     * The position of this camera.
     */
    private Vector3f position;

    /**
     * The view matrix of this camera.
     */
    protected Matrix4f viewMatrix = new Matrix4f();

    /**
     * Getter for the pitch in degrees.
     *
     * @return The pitch of this camera.
     */
    @Override
    public double getPitch() {
        return this.pitch;
    }

    /**
     * Getter for the yaw in degrees.
     *
     * @return The yaw of this camera.
     */
    @Override
    public double getYaw() {
        return this.yaw;
    }

    /**
     * Getter for the roll in degrees.
     *
     * @return The roll of this camera.
     */
    @Override
    public double getRoll() {
        return this.roll;
    }

    /**
     * Setter for the pitch in degrees.
     *
     * @param pitch The new pitch of this camera.
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * Setter for the yaw in degrees.
     *
     * @param yaw The new yaw of this camera.
     */
    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    /**
     * Setter for the roll in degrees.
     *
     * @param roll The new roll of this camera.
     */
    public void setRoll(double roll) {
        this.roll = roll;
    }

    /**
     * Returns whether the camera is active.
     *
     * @return True when this camera is the main camera in the
     * {@link blackengine.rendering.MasterRenderer MasterRenderer}, false
     * otherwise.
     */
    @Override
    public boolean isActive() {
        return RenderEngine.getInstance().getMasterRenderer().getMainCamera() == this;
    }

    /**
     * Activates this camera component by setting the main camera in the
     * {@link blackengine.rendering.MasterRenderer MasterRenderer} to this and
     * setting this active flag to true.
     */
    @Override
    public void onActivate() {
        RenderEngine.getInstance().getMasterRenderer().setMainCamera(this);
    }

    /**
     * Deactivates this camera component by setting the main camera in the
     * {@link blackengine.rendering.MasterRenderer MasterRenderer} to null if
     * and only if this camera component was the main camera. Also sets this
     * components active flag to false.
     */
    @Override
    public void onDeactivate() {
        if (this.isActive()) {
            RenderEngine.getInstance().getMasterRenderer().setMainCamera(null);
        }
    }

    /**
     * Updates the yaw, pitch and roll for this camera, updates its position and
     * creates a view matrix which can be used by the POV Renderers.
     */
    @Override
    public void update() {
        this.updateYawPitchRoll();
        this.updatePosition();
        this.createViewMatrix();
    }

    /**
     * Updates the yaw of this camera to the negative Y-rotation of its parent.
     */
    protected void updateYawPitchRoll() {
        this.yaw = -super.getParent().getTransform().getEulerRotation().getY();
    }

    /**
     * Updates the position of this camera to the absolute position of its
     * parent.
     */
    protected void updatePosition() {
        this.position = new Vector3f(super.getParent().getTransform().getAbsolutePosition());
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

    /**
     * Creates the view matrix for this camera component using its position and
     * rotation.
     */
    private void createViewMatrix() {
        this.viewMatrix = new Matrix4f();
        this.viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(this.getPitch()), new Vector3f(1, 0, 0), this.viewMatrix, this.viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(this.getYaw()), new Vector3f(0, 1, 0), this.viewMatrix, this.viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(this.getRoll()), new Vector3f(0, 0, 1), this.viewMatrix, this.viewMatrix);
        Vector3f negativeCameraPos = this.position.negate(null);
        Matrix4f.translate(negativeCameraPos, this.viewMatrix, this.viewMatrix);
    }

    @Override
    public Vector3f getPosition() {
        return this.position;
    }

}
