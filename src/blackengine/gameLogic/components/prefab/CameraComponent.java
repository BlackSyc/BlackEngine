/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.rendering.Camera;
import blackengine.rendering.RenderEngine;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class CameraComponent extends ComponentBase implements Camera {
    
    private double pitch = 0;
    private double yaw = 0;
    private double roll = 0;
    
    private Vector3f position;
    
    protected Matrix4f viewMatrix = new Matrix4f();

    public double getPitch() {
        return this.pitch;
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getRoll() {
        return this.roll;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }
    
    @Override
    public boolean isActive() {
        return RenderEngine.getInstance().getMasterRenderer().getCamera() == this;
    }

    public CameraComponent() {
    }

    @Override
    public void activate() {
        RenderEngine.getInstance().getMasterRenderer().setCamera(this);
    }

    @Override
    public void update() {
        this.updateYawPitchRoll();
        this.updatePosition();
        this.createViewMatrix();
    }
    
    protected void updateYawPitchRoll(){
        this.yaw = -super.getParent().getRotation().getY();
    }
    
    protected void updatePosition(){
        this.position = new Vector3f(super.getParent().getAbsolutePosition());
    }

    @Override
    public Matrix4f getViewMatrix() {
        return this.viewMatrix;
    }

    public void createViewMatrix() {
        this.viewMatrix = new Matrix4f();
        this.viewMatrix.setIdentity();
        Matrix4f.rotate((float) this.getPitch(), new Vector3f(1, 0, 0), this.viewMatrix, this.viewMatrix);
        Matrix4f.rotate((float) this.getYaw(), new Vector3f(0, 1, 0), this.viewMatrix, this.viewMatrix);
        Matrix4f.rotate((float) this.getRoll(), new Vector3f(0, 0, 1), this.viewMatrix, this.viewMatrix);
        Vector3f negativeCameraPos = this.position.negate(null);
        Matrix4f.translate(negativeCameraPos, this.viewMatrix, this.viewMatrix);
    }

}
