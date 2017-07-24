/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

/**
 * An instance of this class will deal with all rendering logic by coordinating
 * all other rendering classes.
 *
 * @author Blackened
 */
public class MasterRenderer {

    /**
     * All POV renderers belonging to this instance of MasterRenderer, mapped to
     * their class.
     */
    private Map<Class<? extends POVRendererBase>, POVRendererBase> povRenderers;

    /**
     * All flat renderers belonging to this instance of MasterRenderer, mapped
     * to their class.
     */
    private Map<Class<? extends FlatRendererBase>, FlatRendererBase> flatRenderers;

    /**
     * The camera that will be used to render the elements of the scene.
     */
    private Camera camera;

    /**
     * The projection matrix used in this MasterRenderer.
     */
    private Matrix4f projectionMatrix;

    /**
     * Retrieves a POV renderer of the specified class if it is present in this
     * master renderer.
     *
     * @param <T> The type of the POV renderer to be retrieved.
     * @param clazz The class of the POV renderer to be retrieved.
     * @return A POV renderer object of the specified class.
     */
    public <T extends POVRendererBase> T getPOVRenderer(Class<T> clazz) {
        return clazz.cast(this.povRenderers.get(clazz));
    }

    /**
     * Retrieves a flat renderer of the specified class if it is present in this
     * master renderer.
     *
     * @param <T> The type of the flat renderer to be retrieved.
     * @param clazz The class of the flat renderer to be retrieved.
     * @return A flat renderer object of the specified class.
     */
    public <T extends FlatRendererBase> T getFlatRenderer(Class<T> clazz) {
        return clazz.cast(this.flatRenderers.get(clazz));
    }

    /**
     * Getter for the camera object present in this instance of MasterRenderer.
     *
     * @return An object implementing the Camera interface.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Setter for the camera object for this instance of MasterRenderer.
     *
     * @param camera An object implementing the Camera interface.
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Default constructor for creating a new instance of MasterRenderer.
     */
    public MasterRenderer() {
        this.povRenderers = new HashMap<>();
        this.flatRenderers = new HashMap<>();
    }

    /**
     * Adds a new renderer object to this master renderer.
     *
     * @param renderer The renderer to be added.
     */
    public void addPOVRenderer(POVRendererBase renderer) {
        if (this.containsPOVRendererByClass(renderer.getClass())) {
            this.getPOVRenderer(renderer.getClass()).destroy();
        }
        this.povRenderers.put(renderer.getClass(), renderer);
        renderer.setProjectionMatrix(this.projectionMatrix);
        renderer.initialize();
    }

    public void addFlatRenderer(FlatRendererBase renderer) {

    }

    /**
     * Verifies whether a POV renderer of the specified type is present in this
     * master renderer.
     *
     * @param clazz The class which will be used to check whether a POV renderer
     * is present.
     * @return True when a POV renderer of specified type is present, false
     * otherwise.
     */
    public boolean containsPOVRendererByClass(Class<? extends POVRendererBase> clazz) {
        return this.povRenderers.containsKey(clazz);
    }

    /**
     * Verifies whether a flat renderer of the specified type is present in this
     * master renderer.
     *
     * @param clazz The class which will be used to check whether a flat
     * renderer is present.
     * @return True when a flat renderer of the specified type is present, false
     * otherwise.
     */
    public boolean containsFlatRendererByClass(Class<? extends FlatRendererBase> clazz) {
        return this.flatRenderers.containsKey(clazz);
    }

    /**
     * Calls the render method on all renderers present in this master renderer.
     */
    public void render() {
        this.prepareForRendering();
        this.renderPOV();
        this.renderFlat();

    }

    /**
     * Creates a new projection matrix in accordance with the FOV, FAR_PLANE,
     * NEAR_PLANE and display size.
     *
     * @param displayWidth
     * @param displayHeight
     * @param fieldOfView
     * @param nearPlane
     * @param farPlane
     */
    public void createProjectionMatrix(float displayWidth, float displayHeight, float fieldOfView, float farPlane, float nearPlane) {
        float aspectRatio = displayWidth / displayHeight;
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

    /**
     * Destroys all renderers present in this master renderer.
     */
    public void destroy() {
        this.povRenderers.values().forEach(x -> x.destroy());
        this.povRenderers = new HashMap<>();
        this.flatRenderers.values().forEach(x -> x.destroy());
        this.flatRenderers = new HashMap<>();
    }

    /**
     * Calls the render method on all of the registered POV renderers present in
     * this instance of MasterRenderer, in the order provided by the
     * RenderEngine.
     */
    private void renderPOV() {
        if (this.camera != null) {
            Iterator<Class<? extends POVRendererBase>> iter = RenderEngine.getPOVRendererIterator();

            while (iter.hasNext()) {
                Class<? extends POVRendererBase> rendererClass = iter.next();
                if (this.containsPOVRendererByClass(rendererClass)) {
                    this.getPOVRenderer(rendererClass).render(this.camera.getViewMatrix());
                }
            }
        }
    }

    /**
     * Calls the render method on all of the registered flat renderers present
     * in this instance of MasterRenderer, in the order provided by the
     * RenderEngine.
     */
    private void renderFlat() {
        Iterator<Class<? extends FlatRendererBase>> iter = RenderEngine.getFlatRendererIterator();

        while (iter.hasNext()) {
            Class<? extends FlatRendererBase> rendererClass = iter.next();
            if (this.containsFlatRendererByClass(rendererClass)) {
                this.getFlatRenderer(rendererClass).render();
            }
        }
    }

    private void prepareForRendering() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0, 0, 1);

    }

}
