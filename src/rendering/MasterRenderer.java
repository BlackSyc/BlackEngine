/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rendering;

import java.util.Map;

/**
 * An instance of this class will deal with all rendering logic by coordinating
 * all other rendering classes.
 *
 * @author Blackened
 */
public class MasterRenderer {

    /**
     * All renderers belonging to this instance of MasterRenderer, mapped to
     * their class.
     */
    private Map<Class<? extends RendererBase>, RendererBase> renderers;

    /**
     * Retrieves a renderer of the specified class if it is present in this
     * master renderer.
     *
     * @param <T> The type of the renderer to be retrieved.
     * @param clazz The class of the renderer to be retrieved.
     * @return A renderer object of the specified class.
     */
    public <T extends RendererBase> T getRenderer(Class<T> clazz) {
        return clazz.cast(this.renderers.get(clazz));
    }

    /**
     * Adds a new renderer object to this master renderer.
     *
     * @param renderer The renderer to be added.
     */
    public void addRenderer(RendererBase renderer) {
        if (this.containsRendererByClass(renderer.getClass())) {
            this.getRenderer(renderer.getClass()).destroy();
        }
        this.renderers.put(renderer.getClass(), renderer);
    }

    /**
     * Verifies whether a renderer of the specified type is present in this
     * master renderer.
     *
     * @param clazz The class which will be used to check whether a renderer is
     * present.
     * @return True when a renderer of specified type is present, false
     * otherwise.
     */
    public boolean containsRendererByClass(Class<? extends RendererBase> clazz) {
        return this.renderers.containsKey(clazz);
    }

}
