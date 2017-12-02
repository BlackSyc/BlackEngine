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
import blackengine.rendering.pipeline.shaderPrograms.ShaderProgramBase;

/**
 * This interface defines the methods for an element of the pipeline that can be
 * used for rendering and processing.
 *
 * @author Blackened
 * @param <S>
 */
public interface PipelineElement<S extends ShaderProgramBase> {

    /**
     * Retrieves the priority that is assigned to this element. A higher
     * priority means its render method will be called prior to other elements
     * with a lower priority.
     *
     * @return A float representing the priority of this pipeline element.
     */
    public float getPriority();

    /**
     * An implementation of this method should render or process to the
     * presented Camera instance.
     *
     * @param camera An instance of Camera.
     */
    public void render(Camera camera);

    /**
     * Determines whether this element was flagged for destruction.
     *
     * @return True if this elements 'destroyed' flag is true, false otherwise.
     */
    public boolean isDestroyed();

    /**
     * An implementation of this method should destroy all its referenced
     * properties and set its 'destroyed' flag to true.
     */
    public void destroy();

    /**
     * An implementation of this method should return the class of the shader
     * program that is used in this element.
     *
     * @return The class of shader program that is used in this pipeline
     * element.
     */
    @SuppressWarnings("unchecked")
    public Class<S> getShaderClass();

    /**
     * An implementation of this method should handle all initialization needed
     * for this pipeline element to be able to render.
     */
    public void initialize();

    /**
     * An implementation of this setter should set its enabled flag according to
     * the presented boolean value.
     *
     * @param enabled The value the 'enabled' flag will be set to.
     */
    public void setEnabled(boolean enabled);

    /**
     * An implementation of this getter should return the value represented in
     * the 'enabled' property flag.
     *
     * @return True when this pipeline element is enabled, false otherwise.
     */
    public boolean isEnabled();

}
