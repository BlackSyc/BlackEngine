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
import blackengine.toolbox.math.ImmutableVector3;

/**
 *
 * @author Blackened
 */
public class PointLightComponent extends ComponentBase implements PointLight {

    private ImmutableVector3 attenuation;
    private ImmutableVector3 offSet;
    private ImmutableVector3 colour;

    public void setAttenuation(ImmutableVector3 attenuation) {
        this.attenuation = attenuation;
    }

    public void setOffSet(ImmutableVector3 offSet) {
        this.offSet = offSet;
    }

    public void setColour(ImmutableVector3 colour) {
        this.colour = colour;
    }

    @Override
    public ImmutableVector3 getAttenuation() {
        return this.attenuation;
    }

    @Override
    public ImmutableVector3 getPosition() {
        return this.getParent().getTransform().getAbsolutePosition().add(this.offSet);
    }

    @Override
    public ImmutableVector3 getColour() {
        return this.colour;
    }

    public PointLightComponent(ImmutableVector3 attenuation, ImmutableVector3 offSet, ImmutableVector3 colour) {
        this.attenuation = attenuation;
        this.offSet = offSet;
        this.colour = colour;
    }

    public PointLightComponent(ImmutableVector3 attenuation, ImmutableVector3 colour) {
        this(attenuation, new ImmutableVector3(), colour);
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
    public PointLightComponent clone(){
        return new PointLightComponent(this.attenuation.clone(), this.offSet.clone(), this.colour.clone());
    }
}
