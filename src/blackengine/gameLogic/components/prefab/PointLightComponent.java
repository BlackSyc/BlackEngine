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
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.components.base.ComponentBase;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.lighting.PointLight;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class PointLightComponent extends ComponentBase implements PointLight {

    private Vector3f attenuation;
    private Vector3f offSet;
    private Vector3f colour;

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }

    public void setOffSet(Vector3f offSet) {
        this.offSet = offSet;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    @Override
    public Vector3f getAttenuation() {
        return this.attenuation;
    }

    @Override
    public Vector3f getPosition() {
        return Vector3f.add(this.getParent().getTransform().getAbsolutePosition(), this.offSet, null);
    }

    @Override
    public Vector3f getColour() {
        return this.colour;
    }

    public PointLightComponent(Vector3f attenuation, Vector3f offSet, Vector3f colour) {
        this.attenuation = attenuation;
        this.offSet = offSet;
        this.colour = colour;
    }

    public PointLightComponent(Vector3f attenuation, Vector3f colour) {
        this(attenuation, new Vector3f(), colour);
    }
    
    @Override
    public void onActivate(){
        RenderEngine.getInstance().addLight(this);
    }
    
    @Override
    public void onDeactivate(){
        RenderEngine.getInstance().removeLight(this);
    }

    @Override
    public void update() {
    }

}
