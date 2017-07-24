/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

import blackengine.gameLogic.components.base.ComponentBase;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Contains all static settings for the component-entity system.
 *
 * @author Blackened
 */
public class ComponentEngine {

    private static HashMap<Class<? extends ComponentBase>, Float> priorityMap = new HashMap<>();

    private static final SortedSet<Class<? extends ComponentBase>> COMPONENT_ORDER = new TreeSet<>(new Comparator<Class<? extends ComponentBase>>() {

        @Override
        public int compare(Class<? extends ComponentBase> o1, Class<? extends ComponentBase> o2) {
            return priorityMap.get(o1)
                    .compareTo(priorityMap.get(o2));
        }
    });

    ;

    public static HashMap<Class<? extends ComponentBase>, Float> getPriorityMap() {
        return priorityMap;
    }

    public static void registerComponent(Class<? extends ComponentBase> clazz, Float priority) {
        priorityMap.put(clazz, priority);
        COMPONENT_ORDER.add(clazz);
    }

    public static void unregisterComponent(Class<? extends ComponentBase> clazz) {
        priorityMap.remove(clazz);
        COMPONENT_ORDER.remove(clazz);
    }

    public static Iterator<Class<? extends ComponentBase>> getComponentIterator() {
        return COMPONENT_ORDER.iterator();
    }

}
