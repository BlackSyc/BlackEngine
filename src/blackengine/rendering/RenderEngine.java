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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
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
    }
    
    protected void destroy(){
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

    //<editor-fold  defaultstate="collapsed" desc="Settings">
    private boolean anisotropicFilteringEnabled = true;

    public boolean isAnisotropicFilteringEnabled() {
        return this.anisotropicFilteringEnabled;
    }
    //</editor-fold>

    // <editor-fold  defaultstate="collapsed" desc="Renderer registration">
    private HashMap<Class<? extends POVRendererBase>, Float> povPriorityMap = new HashMap<>();

    private final SortedSet<Class<? extends POVRendererBase>> POV_RENDERER_ORDER = new TreeSet<>(new Comparator<Class<? extends POVRendererBase>>() {

        @Override
        public int compare(Class<? extends POVRendererBase> o1, Class<? extends POVRendererBase> o2) {
            return povPriorityMap.get(o1)
                    .compareTo(povPriorityMap.get(o2));
        }
    });

    private HashMap<Class<? extends FlatRendererBase>, Float> flatPriorityMap = new HashMap<>();

    private final SortedSet<Class<? extends FlatRendererBase>> FLAT_RENDERER_ORDER = new TreeSet<>(new Comparator<Class<? extends FlatRendererBase>>() {

        @Override
        public int compare(Class<? extends FlatRendererBase> o1, Class<? extends FlatRendererBase> o2) {
            return flatPriorityMap.get(o1)
                    .compareTo(flatPriorityMap.get(o2));
        }
    });

    public HashMap<Class<? extends POVRendererBase>, Float> getPOVPriorityMap() {
        return this.povPriorityMap;
    }

    public HashMap<Class<? extends FlatRendererBase>, Float> getFlatPriorityMap() {
        return this.flatPriorityMap;
    }

    public void registerPOVRenderer(Class<? extends POVRendererBase> clazz, Float priority) {
        this.povPriorityMap.put(clazz, priority);
        this.POV_RENDERER_ORDER.add(clazz);
    }

    public void registerFlatRenderer(Class<? extends FlatRendererBase> clazz, Float priority) {
        this.flatPriorityMap.put(clazz, priority);
        this.FLAT_RENDERER_ORDER.add(clazz);
    }

    public void unregisterPOVRenderer(Class<? extends POVRendererBase> clazz) {
        this.povPriorityMap.remove(clazz);
        this.POV_RENDERER_ORDER.remove(clazz);
    }

    public void unregisterFlatRenderer(Class<? extends FlatRendererBase> clazz) {
        this.flatPriorityMap.remove(clazz);
        this.FLAT_RENDERER_ORDER.remove(clazz);
    }

    public Iterator<Class<? extends POVRendererBase>> getPOVRendererIterator() {
        return this.POV_RENDERER_ORDER.iterator();
    }

    public Iterator<Class<? extends FlatRendererBase>> getFlatRendererIterator() {
        return this.FLAT_RENDERER_ORDER.iterator();
    }
    //</editor-fold>

}
