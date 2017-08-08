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
package blackengine.gameLogic.components.prefab.behaviour;

import blackengine.gameLogic.components.base.ComponentBase;
import java.util.TreeSet;

/**
 *
 * @author Blackened
 */
public class BehaviourComponent extends ComponentBase {

    private TreeSet<Behaviour> behaviours;

    public BehaviourComponent() {
        this.behaviours = new TreeSet<>((x, y)
                -> Float.compare(x.getPriority(), y.getPriority()));
    }

    public void addBehaviour(Behaviour behaviour) {
        behaviour.setEntity(this.getParent());
        this.behaviours.add(behaviour);
    }

    public void removeBehaviour(Behaviour behaviour) {
        behaviour.setEntity(null);
        this.behaviours.remove(behaviour);
    }

    public void removeBehaviour(String behaviourName) {
        this.behaviours.removeIf(x -> {
            if (x.getName().equals(behaviourName)) {
                x.setEntity(null);
                return true;
            }
            return false;
        });
    }
    
    @Override
    public void activate(){
        this.behaviours.forEach(x -> x.setEntity(this.getParent()));
        super.activate();
    }

    @Override
    public void update() {
        this.behaviours.forEach(x -> x.tick());
    }

}
