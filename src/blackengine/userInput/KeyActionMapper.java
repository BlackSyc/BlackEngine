/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 *
 * @author Blackened
 */
public class KeyActionMapper<T> {
    
    private HashMap<BooleanSupplier, T> keyActionMap;

    public KeyActionMapper(HashMap<BooleanSupplier, T> keyActionMap) {
        this.keyActionMap = keyActionMap;
    }
    
    public Iterator<BooleanSupplier> getMappedKeyIterator(){
        return this.keyActionMap.keySet().iterator();
    }
    
    public Iterator<T> getMappedActionIterator(){
        return this.keyActionMap.values().iterator();
    }
    
    public T getAction(BooleanSupplier key){
        return this.keyActionMap.get(key);
    }
    
}
