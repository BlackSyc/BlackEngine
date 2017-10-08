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
package blackengine.rendering.renderers;

import blackengine.gameLogic.components.base.RenderedComponent;
import blackengine.toolbox.math.ImmutableVector2;
import blackengine.toolbox.math.ImmutableVector3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 * @param <T>
 */
public abstract class RendererBase<T extends RenderedComponent> {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    protected ShaderProgram shaderProgram;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of RenderBase.
     *
     */
    public RendererBase(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    
    public abstract void render();
    
    public abstract void destroy();
    
    public abstract void initialize();
    
    
    /**
     * 
     * @param renderTarget
     */
    public abstract void addRenderTarget(T renderTarget);
    
    /**
     * 
     * @param renderTarget
     */
    public abstract void removeRenderTarget(T renderTarget);
    
    /**
     * 
     * @param renderTarget
     * @return 
     */
    public abstract boolean containsRenderTarget(T renderTarget);
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Protected Methods">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    //</editor-fold>
}
