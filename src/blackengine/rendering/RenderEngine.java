/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.rendering;

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
        return INSTANCE;
    }
    
    protected static void create(){
        INSTANCE = new RenderEngine();
    }

    private RenderEngine() {
        this.masterRenderer = new MasterRenderer();
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
