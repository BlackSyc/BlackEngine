/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

/**
 *
 * @author Blackened
 */
public enum HealthFabricator implements ComponentFabricator<HealthComponent> {

    DEFAULT_HEALTH {
        
        @Override
        public HealthComponent create() {
            HealthComponent newComponent = new HealthComponent();
            return newComponent;
        }

    };
    
    @Override
    public abstract HealthComponent create();
    

}
