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

import blackengine.rendering.renderers.shaders.FragmentShader;
import blackengine.rendering.renderers.shaders.VertexShader;
import blackengine.rendering.renderers.shaders.exceptions.NoSuchAttributeException;
import blackengine.rendering.renderers.shaders.exceptions.ShaderProgramAlreadyInitializedException;
import blackengine.rendering.renderers.shaders.exceptions.UniformVariableNameNotFound;
import blackengine.toolbox.math.ImmutableVector2;
import blackengine.toolbox.math.ImmutableVector3;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 * @param <M>
 */
public abstract class ShaderProgram<M extends Material<? extends ShaderProgram>> {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    /**
     * The ID of this shader program used for reference with the graphics card.
     */
    private int programId = -1;

    /**
     * A HashMap that contains all uniform variable names (and uniform array
     * element names) as keys mapped to their respective location.
     */
    private final HashMap<String, Integer> uniformLocations;

    /**
     * A list of all known attribute names (retrieved from the shaders).
     */
    private final List<String> knownAttributeNames;

    /**
     * The vertex shader for this shader program.
     */
    private final VertexShader vertexShader;

    /**
     * The fragment shader for this program.
     */
    private final FragmentShader fragmentShader;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of ShaderProgram.
     *
     * @param vertexShader The vertex shader that will be used.
     * @param fragmentShader The fragment shader that will be used.
     */
    public ShaderProgram(VertexShader vertexShader, FragmentShader fragmentShader) {

        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;

        this.knownAttributeNames = new ArrayList<>();
        this.uniformLocations = new HashMap<>();

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Loads all properties from the material to their corresponding uniform
     * variables.
     *
     * @param material The material of which the properties will be loaded into
     * the uniform variables.
     */
    public abstract void loadMaterialProperties(M material);

    /**
     * Starts the shader program on the graphics card.
     */
    public void start() {
        GL20.glUseProgram(this.programId);
    }

    /**
     * Stops the shader program on the graphics card.
     */
    public void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Gets called after the initialization has taken place, while the shader
     * program is still running.
     */
    public abstract void onInitialize();

    /**
     * Gets called just before the destroy method is called, while the shader
     * program is still running.
     */
    public abstract void onDestroy();

    public final void initialize() {
        if (this.programId != -1) {
            throw new ShaderProgramAlreadyInitializedException(this);
        }
        this.programId = GL20.glCreateProgram();

        this.vertexShader.attachTo(this.programId);
        this.fragmentShader.attachTo(this.programId);

        Stream.concat(this.vertexShader.getAttributeNames(),
                this.fragmentShader.getAttributeNames())
                .forEach(x -> this.knownAttributeNames.add(x));

        this.validate();

        this.start();
        this.uniformLocations.putAll(this.loadUniformLocation(Stream
                .concat(this.vertexShader.getUniformNames(),
                        this.fragmentShader.getUniformNames())));

        this.onInitialize();
        this.stop();
    }

    public void destroy() {
        this.onDestroy();
        this.vertexShader.destroy();
        this.fragmentShader.destroy();
        GL20.glDeleteProgram(this.programId);
    }

    /**
     * Loads a matrix to the uniform variable in the specified location by
     * converting it to a float buffer first.
     *
     * @param uniformName The name of the uniform variable the matrix will be
     * loaded into.
     * @param matrix The matrix that needs to be loaded into the uniform
     * variable.
     */
    public void loadUniformMatrix(String uniformName, Matrix4f matrix) {
        int uniformLocation = this.loadUniformLocation(uniformName);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(uniformLocation, false, matrixBuffer);
    }

    /**
     * Loads a boolean value to the specified uniform.
     *
     * @param uniformName The name of the uniform variable the boolean will be
     * loaded into.
     * @param value The boolean value that will be loaded into the uniform
     * variable.
     */
    public void loadUniformBool(String uniformName, boolean value) {
        GL20.glUniform1i(this.getUniformLocation(uniformName), value ? 1 : 0);
    }

    /**
     * Loads an instance of ImmutableVector3 to the specified uniform.
     *
     * @param uniformName The name of the uniform variable the vector will be
     * loaded into.
     * @param vector The instance of ImmutableVector3 that will be loaded into
     * the uniform variable.
     */
    public void loadUniformVector3f(String uniformName, ImmutableVector3 vector) {
        GL20.glUniform3f(this.getUniformLocation(uniformName), vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Loads an instance of ImmutableVector2 to the specified uniform.
     *
     * @param uniformName The name of the uniform variable the vector will be
     * loaded into.
     * @param vector The instance of ImmutableVector2 that will be loaded into
     * the uniform variable.
     */
    public void loadUniformVector2f(String uniformName, ImmutableVector2 vector) {
        GL20.glUniform2f(this.getUniformLocation(uniformName), vector.getX(), vector.getY());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Protected methods">
    /**
     * Binds the attribute name ('in' variable) to the given location for later
     * reference.
     *
     * @param attributeName The name of the attribute that will be bound to the
     * location.
     * @param location The location the attribute will be bound to.
     */
    protected void bindAttribute(String attributeName, int location) {
        if (!this.knownAttributeNames.contains(attributeName)) {
            throw new NoSuchAttributeException(attributeName);
        }
        GL20.glBindAttribLocation(this.programId, location, attributeName);
    }

    /**
     * An implementation of this method should bind all attributes ('in'
     * variables) to a specified location using the member
     * {@link #bindAttribute(java.lang.String, int) bindAttribute(attributeName, location)}.
     */
    protected abstract void bindAttributes();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">
    private int getUniformLocation(String uniformVariableName) {
        if (!this.uniformLocations.containsKey(uniformVariableName)) {
            throw new UniformVariableNameNotFound(uniformVariableName);
        }
        return this.uniformLocations.get(uniformVariableName);
    }

    /**
     * Binds the attributes ('in' variables) and validates this program.
     */
    private void validate() {
        this.bindAttributes();

        GL20.glLinkProgram(this.programId);
        GL20.glValidateProgram(this.programId);
//        if (GL20.glGetProgrami(this.programId, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
//            throw new ShaderProgramCompileException(this.getClass());
//        }
    }

    /**
     * Retrieves all uniform variable locations using the member
     * {@link #loadUniformLocation(java.lang.String) loadUniformLocation(uniformVariableName)}.
     *
     * @param uniformNames A stream of all uniform variable names for which the
     * location will be retrieved.
     * @return A HashMap containing the uniform variable name mapped to their
     * location.
     */
    private HashMap<String, Integer> loadUniformLocation(Stream<String> uniformNames) {
        HashMap<String, Integer> map = new HashMap<>();
        uniformNames.forEach(x -> {
            map.put(x, this.loadUniformLocation(x));
        });
        return map;
    }

    /**
     * Loads the location for a uniform variable or uniform array element.
     *
     * @param uniformVariableName The name of the uniform variable or uniform
     * array element (including the brackets and index) for which the location
     * will be returned.
     * @return The location of a uniform variable specified by the name.
     */
    private int loadUniformLocation(String uniformVariableName) {
        return GL20.glGetUniformLocation(this.programId, uniformVariableName);
    }
    //</editor-fold>

}
