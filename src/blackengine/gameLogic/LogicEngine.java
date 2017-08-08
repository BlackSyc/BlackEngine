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
