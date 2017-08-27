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

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class Transform {
    
    
    private Entity parent;
    
    private Vector3f position;
    
    private Vector3f eulerRotation;
    
    private Vector3f scale;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getEulerRotation() {
        return eulerRotation;
    }

    public void setEulerRotation(Vector3f eulerRotation) {
        this.eulerRotation = eulerRotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
    //</editor-fold>

    public Transform(Entity parent, Vector3f position, Vector3f eulerRotation, Vector3f scale) {
        this.parent = parent;
        this.position = position;
        this.eulerRotation = eulerRotation;
        this.scale = scale;
    }
    
    public Vector3f getAbsolutePosition(){
        if(this.parent.getParent() != null){
            return Vector3f.add(this.parent.getParent().getTransform().getAbsolutePosition(), this.getPosition(), null);
        }
        return new Vector3f(this.position);
    }    
}
