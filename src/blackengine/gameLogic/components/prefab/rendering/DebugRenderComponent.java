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
package blackengine.gameLogic.components.prefab.rendering;

import blackengine.gameLogic.components.base.POVRendereredComponentBase;
import blackengine.openGL.texture.Texture;
import blackengine.openGL.vao.Vao;
import blackengine.rendering.prefab.testing.DebugRenderer;

/**
 *
 * @author Blackened
 */
public class DebugRenderComponent extends POVRendereredComponentBase<DebugRenderer>{
    
    private Vao vao;
    
    private Texture texture;
    
    private boolean wireFrameEnabled = false;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isWireFrameEnabled() {
        return wireFrameEnabled || this.texture == null;
    }

    
    public boolean setWireFrameEnabled(boolean wireFrameEnabled) {
        this.wireFrameEnabled = wireFrameEnabled;
        return this.texture != null;
    }

    public Vao getVao() {
        return vao;
    }
    
    public DebugRenderComponent(Vao vao, DebugRenderer renderer) {
        super(renderer);
        
        this.vao = vao;
    }
        

    @Override
    public boolean isRendered() {
        if(super.getRenderer() != null){
            super.getRenderer().containsRenderTarget(this);
        }
        return false;
    }

    @Override
    public void onActivate() {
        super.getRenderer().addRenderTarget(this);
    }

    @Override
    public void onDeactivate() {
        super.getRenderer().removeRenderTarget(this);
    }
    
    @Override
    public DebugRenderComponent clone(){
        return new DebugRenderComponent(this.vao, this.getRenderer());
    }

    
}
