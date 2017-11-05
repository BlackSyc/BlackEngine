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
package blackengine.rendering.pipeline.framebuilding;

import blackengine.rendering.RenderEngine;
import blackengine.rendering.pipeline.shaderPrograms.ProcessingShaderProgram;
import blackengine.toolbox.math.ImmutableVector3;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Blackened
 */
public class FrameBuilder {
    
    private final List<FrameProcessor> frameProcessors;
    
    private ImmutableVector3 clearColour;

    public ImmutableVector3 getClearColour() {
        return clearColour;
    }

    public void setClearColour(ImmutableVector3 clearColour) {
        this.clearColour = clearColour;
    }
    
    

    public FrameBuilder() {
        this.frameProcessors = new ArrayList<>();
        this.clearColour = new ImmutableVector3(0.5f, 0.5f, 0);
    }
    
    public void addFrameProcessor(FrameProcessor frameProcessor){
        this.frameProcessors.add(frameProcessor);
        this.frameProcessors.sort((x,y) -> Float.compare(y.getPriority(), x.getPriority()));
        frameProcessor.initialize();
    }    
    
    public void buildFrame(){
        GL11.glClearColor(this.clearColour.getX(), this.clearColour.getY(), this.clearColour.getZ(), 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        
        
        this.frameProcessors.stream()
                .filter(x -> x.isEnabled())
                .forEach(x -> x.render());
    }
    
    
    
}
