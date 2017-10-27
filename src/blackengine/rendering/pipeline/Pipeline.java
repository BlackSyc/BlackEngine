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
package blackengine.rendering.pipeline;

import blackengine.rendering.pipeline.elements.PipelineElement;
import blackengine.rendering.pipeline.shaderPrograms.ShaderProgramBase;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Blackened
 */
public class Pipeline {
    
    private final List<PipelineEntry> pipeline;
    
    private final Comparator<PipelineEntry> comparator;
    
    
    /**
     * Getter for stream of the rendering pipeline, ordered by their rendering
     * priority.
     *
     * @return An instance of a Stream of pipeline elements that is ordered
     * according to the rendering priority.
     */
    public Stream<PipelineElement> stream() {
        return this.pipeline.stream().map(x -> x.getPipelineElement());
    }

    public Pipeline() {
        this.pipeline = new ArrayList<>();
        this.comparator = (x,y) -> Float.compare(y.getPriority(), x.getPriority());
    }
    
    public void add(PipelineElement pipelineElement, float priority){
        this.pipeline.add(new PipelineEntry(pipelineElement, priority));
        this.pipeline.sort(this.comparator);
    }
    
    public void remove(PipelineElement pipelineElement){
        this.pipeline.removeIf(x -> x.getPipelineElement() == pipelineElement);
    }
    
    public void removeElementWith(Class<? extends ShaderProgramBase> shaderProgramClass){
        this.pipeline.removeIf(x -> x.getPipelineElement().getShaderClass() == shaderProgramClass);
    }
    
}
