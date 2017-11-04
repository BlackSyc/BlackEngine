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

import blackengine.gameLogic.components.prefab.CameraComponent;
import blackengine.openGL.frameBuffer.FrameBufferObject;
import blackengine.rendering.exceptions.DuplicateCamereIdentifierException;
import blackengine.rendering.exceptions.RenderEngineNotCreatedException;
import blackengine.rendering.lighting.Light;
import blackengine.rendering.pipeline.PipelineManager;
import blackengine.rendering.pipeline.Resolution;
import blackengine.rendering.pipeline.framebuilding.FrameBuilder;
import blackengine.toolbox.Guard;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        this.cameras = new HashMap<>();
        this.pipelineManager = new PipelineManager();
        this.frameBuilder = new FrameBuilder();

    }

    /**
     * Destroys this instance of RenderEngine and its master renderer.
     */
    protected void destroy() {
        this.pipelineManager.destroy();
        INSTANCE = null;
    }
    //</editor-fold>

    //<editor-fold desc="Pipeline" defaultstate="collapsed">
    /**
     * The pipeline manager that contains the rendering pipeline containing all
     * renderers and processors ordered by their render priority.
     */
    private final PipelineManager pipelineManager;

    /**
     * Getter for the pipeline manager containing the pipeline used for
     * rendering and processing.
     *
     * @return The instance of PipelineManager that is currently being used.
     */
    public PipelineManager getPipelineManager() {
        return this.pipelineManager;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FBO">
    /**
     * All frame buffer objects mapped to their name.
     */
    private final HashMap<String, FrameBufferObject> fbos;

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
    public void render() {
        this.pipelineManager.removeDestroyedPipelineElements();
        this.cameras.values().stream()
                .filter(x -> x.isActive())
                .forEach(x -> x.render());

        this.frameBuilder.buildFrame();
    }
    //</editor-fold>

    // <editor-fold  defaultstate="collapsed" desc="Cameras">
    private Map<String, CameraComponent> cameras;

    public void addCamera(CameraComponent cameraComponent) {
        Guard.notNull(cameraComponent);

        if (this.cameras.keySet().contains(cameraComponent.getIdentifier())) {
            throw new DuplicateCamereIdentifierException(cameraComponent.getIdentifier());
        }

        this.cameras.put(cameraComponent.getIdentifier(), cameraComponent);

        this.cameras = this.cameras.entrySet()
                .stream()
                .sorted((x, y) -> Float.compare(x.getValue().getPriority(), y.getValue().getPriority()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public CameraComponent getCamera(String identifier) {
        return this.cameras.get(identifier);
    }

    public void removeCamera(CameraComponent cameraComponent) {
        this.cameras.remove(cameraComponent.getIdentifier());
    }

    public void removeCamera(String identifier) {
        this.cameras.remove(identifier);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Frame Building">
    private final FrameBuilder frameBuilder;
    
    private Resolution frameResolution;
    
    public void setFrameResolution(Resolution frameResolution){
        this.frameResolution = frameResolution;
    }
    
    public Resolution getFrameResolution(){
        return this.frameResolution;
    }

    public FrameBuilder getFrameBuilder() {
        return this.frameBuilder;
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
