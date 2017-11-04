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

import blackengine.rendering.pipeline.shaderPrograms.ProcessingShaderProgram;

/**
 *
 * @author Blackened
 * @param <S>
 */
public class FrameProcessor<S extends ProcessingShaderProgram> {
    
    private final float priority;

    private boolean destroyed = false;

    private boolean enabled = true;

    private final S shaderProgram;
    
    public S getShaderProgram(){
        return this.shaderProgram;
    }

    public FrameProcessor(S shaderProgram) {
        this(shaderProgram, 1.0f);
    }

    /**
     * Constructor for creating a new instance of FrameProcessor.
     *
     * @param shaderProgram The shader program that will be used in this
     * frame processor.
     * @param priority A float determining the priority index used to determine
     * the order of the rendering pipeline. A higher priority means it will be
     * rendered earlier in the pipeline.
     */
    public FrameProcessor(S shaderProgram, float priority) {
        this.shaderProgram = shaderProgram;
        this.priority = priority;
    }

    public float getPriority() {
        return this.priority;
    }

    public void render() {
        if (this.isEnabled()) {
            this.shaderProgram.start(null);
            this.shaderProgram.applySettings();
            this.shaderProgram.loadFrameUniforms();
            this.shaderProgram.process();
            this.shaderProgram.revertSettings();
            this.shaderProgram.stop();
        }
    }

    @SuppressWarnings("unchecked")
    public Class<S> getShaderClass() {
        return (Class<S>) this.shaderProgram.getClass();
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void destroy() {
        this.shaderProgram.destroy();
        this.destroyed = true;
    }

    public void initialize() {
        this.shaderProgram.initialize();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
    
}
