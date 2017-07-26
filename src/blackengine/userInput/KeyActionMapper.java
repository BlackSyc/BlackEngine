/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.userInput;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Blackened
 */
public class KeyActionMapper<T> {
    
    private HashMap<Integer, T> keyActionMap;

    public KeyActionMapper(HashMap<Integer, T> keyActionMap) {
        this.keyActionMap = keyActionMap;
    }
    
    public Iterator<Integer> getMappedKeyIterator(){
        return this.keyActionMap.keySet().iterator();
    }
    
    public Iterator<T> getMappedActionIterator(){
        return this.keyActionMap.values().iterator();
    }
    
    public T getAction(Integer key){
        return this.keyActionMap.get(key);
    }
    
}
