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
package blackengine.rendering;

import blackengine.rendering.exceptions.RenderEngineNotCreatedException;
import blackengine.rendering.lighting.Light;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Engine singleton for rendering management.
 *
 * @author Blackened
 */
public class RenderEngine {

    //<editor-fold defaultstate="collapsed" desc="Instance">
    private static RenderEngine INSTANCE;

    public static RenderEngine getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new RenderEngineNotCreatedException();
    }

    protected static void create() {
        INSTANCE = new RenderEngine();
    }

    private RenderEngine() {
        this.masterRenderer = new MasterRenderer();
        this.lights = new ArrayList<>();
    }

    protected void destroy() {
        this.masterRenderer.destroy();
        INSTANCE = null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Master Renderer">
    private MasterRenderer masterRenderer;

    public MasterRenderer getMasterRenderer() {
        return this.masterRenderer;
    }
    //</editor-fold>
    
    private Camera mainCamera;
    
    private Matrix4f projectionMatrix = new Matrix4f();

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    //<editor-fold  defaultstate="collapsed" desc="Settings">
    private boolean anisotropicFilteringEnabled = true;

    public boolean isAnisotropicFilteringEnabled() {
        return this.anisotropicFilteringEnabled;
    }
    //</editor-fold>
    
        /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     *
     * @param fieldOfView
     * @param nearPlane
     * @param farPlane
     */
    public void createProjectionMatrix(float width, float height, float fieldOfView, float farPlane, float nearPlane) {
        float aspectRatio = width / height;
        float y_scale = (float) (1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
        this.projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
        this.projectionMatrix.m23 = -1;
        this.projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
        this.projectionMatrix.m33 = 0;
    }

    // <editor-fold  defaultstate="collapsed" desc="Lighting">
    /**
     * A list of all lights that can be used for rendering.
     */
    private final ArrayList<Light> lights;

    /**
     * Retrieves a stream of all lights that can be used for rendering.
     *
     * @return
     */
    public Stream<Light> getLightStream() {
        return lights.stream();
    }

    /**
     * Adds a light to the render engine.
     *
     * @param light An instance of Light that can be used for rendering.
     */
    public void addLight(Light light) {
        this.lights.add(light);
    }

    /**
     * Removes a light from the render engine.
     *
     * @param light An instance of Light that will no longer be used for
     * rendering.
     */
    public void removeLight(Light light) {
        this.lights.remove(light);
    }

    /**
     * Removes all lights from the render engine.
     */
    public void removeAllLights() {
        this.lights.clear();
    }
    //</editor-fold>
}
