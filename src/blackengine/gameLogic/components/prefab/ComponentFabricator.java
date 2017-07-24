/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.components.base.ComponentBase;

/**
 * An implementation of this interface is used when creating a new component
 * from the addComponent method within the Entity class. Enums extending this 
 * interface are easily used.
 *
 * @author Blackened
 * @param <T> The component class this fabricator will create an instance of.
 */
public interface ComponentFabricator<T extends ComponentBase> {

    public T create();

}
