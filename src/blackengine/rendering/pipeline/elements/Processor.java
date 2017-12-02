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
package blackengine.rendering.pipeline.elements;

import blackengine.rendering.Camera;
import blackengine.rendering.pipeline.shaderPrograms.ProcessingShaderProgram;

/**
 * An instance of this class will process the rendered image.
 *
 * @author Blackened
 * @param <S>
 */
public class Processor<S extends ProcessingShaderProgram> implements PipelineElement<S> {

    /**
     * The priority of this processor. A higher priority means its render method
     * will be called earlier in the pipeline.
     */
    private final float priority;

    /**
     * Flags whether this processor was destroyed.
     */
    private boolean destroyed = false;

    /**
     * Flags whether this processor is enabled.
     */
    private boolean enabled = true;

    /**
     * The shader program that is being used to process the rendered image.
     */
    private final S shaderProgram;

    /**
     * Getter for the shader program of this processor.
     *
     * @return The shader program that is being used to process the rendered
     * image.
     */
    public S getShaderProgram() {
        return this.shaderProgram;
    }

    /**
     * Default constructor for creating a new instance of Processor. The
     * priority of this processor will be defaulted to 1.0f.
     *
     * @param shaderProgram The shader program that will be used to process the
     * rendered image.
     */
    public Processor(S shaderProgram) {
        this(shaderProgram, 1.0f);
    }

    /**
     * Constructor for creating a new instance of Processor.
     *
     * @param shaderProgram The shader program that will be used in this
     * renderer.
     * @param priority A float determining the priority index used to determine
     * the order of the rendering pipeline. A higher priority means it will be
     * rendered earlier in the pipeline.
     */
    public Processor(S shaderProgram, float priority) {
        this.shaderProgram = shaderProgram;
        this.priority = priority;
    }

    @Override
    public float getPriority() {
        return this.priority;
    }

    @Override
    public void render(Camera camera) {
        if (this.isEnabled()) {
            this.shaderProgram.start(camera);
            this.shaderProgram.applySettings();
            this.shaderProgram.loadFrameUniforms();
            this.shaderProgram.process();
            this.shaderProgram.revertSettings();
            this.shaderProgram.stop();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<S> getShaderClass() {
        return (Class<S>) this.shaderProgram.getClass();
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }

    @Override
    public void destroy() {
        this.shaderProgram.destroy();
        this.destroyed = true;
    }

    @Override
    public void initialize() {
        this.shaderProgram.initialize();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
