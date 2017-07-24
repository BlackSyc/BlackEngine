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
    
    private static boolean anisotropicFilteringEnabled = true;
    
    private static HashMap<Class<? extends POVRendererBase>, Float> povPriorityMap = new HashMap<>();

    private static final SortedSet<Class<? extends POVRendererBase>> POV_RENDERER_ORDER = new TreeSet<>(new Comparator<Class<? extends POVRendererBase>>() {

        @Override
        public int compare(Class<? extends POVRendererBase> o1, Class<? extends POVRendererBase> o2) {
            return povPriorityMap.get(o1)
                    .compareTo(povPriorityMap.get(o2));
        }
    });
    
    private static HashMap<Class<? extends FlatRendererBase>, Float> flatPriorityMap = new HashMap<>();

    private static final SortedSet<Class<? extends FlatRendererBase>> FLAT_RENDERER_ORDER = new TreeSet<>(new Comparator<Class<? extends FlatRendererBase>>() {

        @Override
        public int compare(Class<? extends FlatRendererBase> o1, Class<? extends FlatRendererBase> o2) {
            return flatPriorityMap.get(o1)
                    .compareTo(flatPriorityMap.get(o2));
        }
    });

    public static boolean isAnisotropicFilteringEnabled() {
        return anisotropicFilteringEnabled;
    }
    
    public static HashMap<Class<? extends POVRendererBase>, Float> getPOVPriorityMap() {
        return povPriorityMap;
    }
    
    public static HashMap<Class<? extends FlatRendererBase>, Float> getFlatPriorityMap(){
        return flatPriorityMap;
    }

    public static void registerPOVRenderer(Class<? extends POVRendererBase> clazz, Float priority) {
        povPriorityMap.put(clazz, priority);
        POV_RENDERER_ORDER.add(clazz);
    }
    
    public static void registerFlatRenderer(Class<? extends FlatRendererBase> clazz, Float priority){
        flatPriorityMap.put(clazz, priority);
        FLAT_RENDERER_ORDER.add(clazz);
    }

    public static void unregisterPOVRenderer(Class<? extends POVRendererBase> clazz) {
        povPriorityMap.remove(clazz);
        POV_RENDERER_ORDER.remove(clazz);
    }
    
    public static void unregisterFlatRenderer(Class<? extends FlatRendererBase> clazz){
        flatPriorityMap.remove(clazz);
        FLAT_RENDERER_ORDER.remove(clazz);
    }

    public static Iterator<Class<? extends POVRendererBase>> getPOVRendererIterator() {
        return POV_RENDERER_ORDER.iterator();
    }
    
    public static Iterator<Class<? extends FlatRendererBase>> getFlatRendererIterator(){
        return FLAT_RENDERER_ORDER.iterator();
    }

}
