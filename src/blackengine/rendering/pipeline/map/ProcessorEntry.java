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
package blackengine.rendering.pipeline.map;

import blackengine.rendering.pipeline.elements.Processor;
import blackengine.rendering.pipeline.shaderPrograms.ProcessingShaderProgram;

/**
 *
 * @author Blackened
 * @param <S>
 */
public class ProcessorEntry<S extends ProcessingShaderProgram> {
    
    /**
     * The class of the shader the renderer uses.
     */
    private final Class<S> shaderClass;

    /**
     * The renderer itself.
     */
    private final Processor<S> processor;

    /**
     * Getter for the class of the shader this renderer uses.
     *
     * @return The class of the shader the renderer uses.
     */
    public Class<S> getShaderClass() {
        return shaderClass;
    }

    /**
     * Getter for the renderer itself.
     *
     * @return The renderer itself.
     */
    public Processor<S> getProcessor() {
        return this.processor;
    }

    /**
     * Protected default constructor for creating a new instance of
     * RendererEntry.
     *
     * @param shaderClass The class of the shader the renderer uses.
     * @param processor
     */
    public ProcessorEntry(Class<S> shaderClass, Processor<S> processor) {
        this.shaderClass = shaderClass;
        this.processor = processor;
    }

}
