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

import blackengine.openGL.frameBuffer.FrameBufferObject;
import blackengine.rendering.exceptions.RenderEngineNotCreatedException;
import blackengine.rendering.lighting.Light;
import blackengine.rendering.pipeline.PipelineManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Engine singleton for rendering management.
 *
 * @author Blackened
 */
public class RenderEngine {

    //<editor-fold defaultstate="collapsed" desc="Instance">
    /**
     * The singleton instance of RenderEngine.
     */
    private static RenderEngine INSTANCE;

    /**
     * Retrieves the singleton instance of RenderEngine if it was created or
     * throws a
     * {@link blackengine.rendering.exceptions.RenderEngineNotCreatedException}
     * if it has not yet been instantiated.
     *
     * @return An instance of RenderEngine.
     */
    public static RenderEngine getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new RenderEngineNotCreatedException();
    }

    /**
     * Creates a new singleton instance of RenderEngine.
     */
    protected static void create() {
        INSTANCE = new RenderEngine();
    }

    /**
     * Default constructor for creating a new instance of RenderEngine.
     */
    private RenderEngine() {
        this.lights = new ArrayList<>();
        this.fbos = new HashMap<>();
        this.pipelineManager = new PipelineManager();
    }

    /**
     * Destroys this instance of RenderEngine and its master renderer.
     */
    protected void destroy() {
        this.pipelineManager.destroy();
        INSTANCE = null;
    }
    //</editor-fold>

    //<editor-fold desc="Fields" defaultstate="collapsed">
    /**
     * The pipeline manager that contains the rendering pipeline containing all
     * renderers and processors ordered by their render priority.
     */
    private final PipelineManager pipelineManager;

    /**
     * The width of the display that will be rendered to.
     */
    private int displayWidth;

    /**
     * The height of the display that will be rendered to.
     */
    private int displayHeight;

    /**
     * The main camera that will be used for rendering.
     */
    private Camera mainCamera;

    /**
     * The projection matrix that will be used for rendering.
     */
    private Matrix4f projectionMatrix = new Matrix4f();

    /**
     * All frame buffer objects mapped to their name.
     */
    private final HashMap<String, FrameBufferObject> fbos;
    //</editor-fold>

    //<editor-fold desc="Getters & Setters" defaultstate="collapsed">
    /**
     * Getter for the pipeline manager containing the pipeline used for
     * rendering and processing.
     *
     * @return The instance of PipelineManager that is currently being used.
     */
    public PipelineManager getPipelineManager() {
        return this.pipelineManager;
    }

    /**
     * Getter for the width of the display that will be rendered to.
     *
     * @return An integer representing the width of the display that will be
     * rendered to in pixels.
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     * Setter for the width of the display that will be rendered to.
     *
     * @param displayWidth An integer representing the width of the display that
     * will be rendered to in pixels.
     */
    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    /**
     * Getter for the height of the display that will be rendered to.
     *
     * @return An integer representing the height of the display that will be
     * rendered to in pixels.
     */
    public int getDisplayHeight() {
        return displayHeight;
    }

    /**
     * Setter for the height of the display that will be rendered to.
     *
     * @param displayHeight An integer representing the height of the display
     * that will be rendered to in pixels.
     */
    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    /**
     * Getter for the projection matrix.
     *
     * @return An instance of Matrix4f.
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Getter for the main camera that will be used for rendering.
     *
     * @return An implementation of {@link blackengine.rendering.Camera}.
     */
    public Camera getMainCamera() {
        return mainCamera;
    }

    /**
     * Setter for the main camera that will be used for rendering.
     *
     * @param mainCamera An implementation of
     * {@link blackengine.rendering.Camera}.
     */
    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    /**
     * Getter for a specific frame buffer object.
     *
     * @param name The name the frame buffer object is mapped to.
     * @return An instance of
     * {@link blackengine.openGL.frameBuffer.FrameBufferObject} that is mapped
     * to the specified name.
     */
    public FrameBufferObject getFbo(String name) {
        return fbos.get(name);
    }

    /**
     * Adds a new frame buffer object to the render engine, mapped to the
     * specified name.
     *
     * @param name A String representing the name that the frame buffer object
     * will be mapped to.
     * @param fbo An instance of FrameBufferObject.
     */
    public void addFbo(String name, FrameBufferObject fbo) {
        this.fbos.put(name, fbo);
    }
    //</editor-fold>

    //<editor-fold  defaultstate="collapsed" desc="Settings">
    /**
     * A flag determining whether anisotropic filtering is enabled.
     */
    private boolean anisotropicFilteringEnabled = true;

    /**
     * Getter for the flag whether anisotropic filtering is enabled.
     *
     * @return True if anisotropic filtering is enabled, false otherwise.
     */
    public boolean isAnisotropicFilteringEnabled() {
        return this.anisotropicFilteringEnabled;
    }
    //</editor-fold>

    //<editor-fold desc="Public methods" defaultstate="collapsed">
    /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     *
     * @param fieldOfView
     * @param nearPlane
     * @param farPlane
     */
    public void createProjectionMatrix(float fieldOfView, float farPlane, float nearPlane) {
        float aspectRatio = this.displayWidth / this.displayHeight;
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

    public void render() {
        this.pipelineManager.removeDestroyedPipelineElements();
        this.pipelineManager.getPipeline().forEach(x -> x.render());
    }
    //</editor-fold>

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
