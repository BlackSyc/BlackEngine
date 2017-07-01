/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic.components.mapping;

/**
 * Contains a static reference to an instance of ComponentPriorityMap for use in
 * instances of the Entity class for determining the order in which components
 * update methods will be called.
 *
 * @author Blackened
 */
public class MappingPriority {

    /**
     * The current priority map used for the order of update calls on components
     * within an entity.
     */
    private static ComponentPriorityMap priorityMap
            = ComponentPriorityMap.createDefault();

    /**
     * Getter for the priority map of components.
     *
     * @return An instance of ComponentPriorityMap used in instances of the
     * Entity class for determining the order in which components update methods
     * will be called.
     */
    public static ComponentPriorityMap getPriorityMap() {
        return priorityMap;
    }

    /**
     * Setter for the priority map.
     *
     * @param newPriorityMap An instance of ComponentPriorityMap for determining
     * the order in which components update methods will be called from an
     * entity.
     */
    private static void setPriorityMap(ComponentPriorityMap newPriorityMap) {
        priorityMap = newPriorityMap;
    }

}
