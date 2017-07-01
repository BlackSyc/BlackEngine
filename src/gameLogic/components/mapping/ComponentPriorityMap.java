/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic.components.mapping;

import gameLogic.components.ComponentBase;
import gameLogic.components.prefab.HealthComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Contains a list of classes that extend the ComponentBase class for
 * determining the order in which the components update methods will be called.
 *
 * @author Blackened
 */
public class ComponentPriorityMap {

    /**
     * The list of classes extending ComponentBase in order.
     */
    private List<Class<? extends ComponentBase>> priorityList;

    /**
     * Getter for a list of classes that extend the ComponentBase class for
     * determining the order in which the components update methods will be
     * called.
     *
     * @return A list of classes that extend the ComponentBase class for
     * determining the order in which the components update methods will be
     * called.
     */
    public List<Class<? extends ComponentBase>> getPriorityList() {
        return priorityList;
    }

    /**
     * Default constructor for creating a new instance of ComponentPriorityMap.
     *
     * @param list The list of classes that extend the ComponentBase class for
     * determining the order in which the components update methods will be
     * called.
     */
    public ComponentPriorityMap(List<Class<? extends ComponentBase>> list) {
        this.priorityList = Collections.unmodifiableList(list);
    }

    /**
     * Creates a default list of classes that extend the ComponentBase class for
     * determining the order in which the components update methods will be
     * called. This list only contains components that are present in the
     * BlackEngine library.
     *
     * @return A new instance of ComponentPriorityMap.
     */
    public static ComponentPriorityMap createDefault() {
        List<Class<? extends ComponentBase>> priorityCollection = new ArrayList<>();
        priorityCollection.add(HealthComponent.class);
        return new ComponentPriorityMap(priorityCollection);

    }

}
