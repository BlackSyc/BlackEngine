/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.gameLogic.exceptions.LogicEngineNotCreatedException;
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
public class LogicEngine {

    //<editor-fold> defaultstate="collapsed" desc"Instance">
    private static LogicEngine INSTANCE;

    public static LogicEngine getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new LogicEngineNotCreatedException();
    }

    private LogicEngine() {
    }

    protected static void create() {
        INSTANCE = new LogicEngine();
    }

    protected void destroy() {
        INSTANCE = null;
    }

    //</editor-fold>
    private HashMap<Class<? extends ComponentBase>, Float> priorityMap = new HashMap<>();

    private final SortedSet<Class<? extends ComponentBase>> COMPONENT_ORDER = new TreeSet<>(new Comparator<Class<? extends ComponentBase>>() {

        @Override
        public int compare(Class<? extends ComponentBase> o1, Class<? extends ComponentBase> o2) {
            return priorityMap.get(o1)
                    .compareTo(priorityMap.get(o2));
        }
    });

    private FrameTimer timer = new FrameTimer();

    public FrameTimer getTimer() {
        return timer;
    }

    public HashMap<Class<? extends ComponentBase>, Float> getPriorityMap() {
        return this.priorityMap;
    }

    public void registerComponent(Class<? extends ComponentBase> clazz, Float priority) {
        priorityMap.put(clazz, priority);
        COMPONENT_ORDER.add(clazz);
    }

    public void unregisterComponent(Class<? extends ComponentBase> clazz) {
        priorityMap.remove(clazz);
        COMPONENT_ORDER.remove(clazz);
    }

    public Iterator<Class<? extends ComponentBase>> getComponentIterator() {
        return COMPONENT_ORDER.iterator();
    }

}
