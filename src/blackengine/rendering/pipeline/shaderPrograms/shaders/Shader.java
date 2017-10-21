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
package blackengine.rendering.pipeline.shaderPrograms.shaders;

import blackengine.rendering.pipeline.shaderPrograms.shaders.exceptions.DeadShaderReferenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Blackened
 */
public abstract class Shader {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    /**
     * The ID of this shader used for reference with the graphics card.
     */
    private final int shaderId;

    /**
     * The shader program ID this shader is attached to. This field equals -1 if
     * the shader is not attached to any shader program.
     */
    private int shaderProgramId;

    /**
     * The name of this shader.
     */
    private final String name;

    /**
     * A boolean representing whether this shader was destroyed.
     */
    private boolean destroyed = false;

    /**
     * A list of all uniform variable names and uniform array element names.
     */
    private final List<String> uniformNames;

    /**
     * A list of all attribute names in the shader source.
     */
    private final List<String> attributeNames;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Getter for the uniform names found in this shader, including the
     * separated uniform array element names.
     *
     * @return An instance of Stream containing the uniform variable names and
     * uniform array element names.
     */
    public Stream<String> getUniformNames() {
        return uniformNames.stream();
    }

    /**
     * Getter for the attribute names found in this shader.
     *
     * @return An instance of Stream containing the attribute names.
     */
    public Stream<String> getAttributeNames() {
        return this.attributeNames.stream();
    }

    /**
     * Getter for the name of this shader.
     *
     * @return An instance of String representing the name of this shader.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for whether this shader is attached to a shader program or not.
     *
     * @return True if this shader is attached to a valid shader program, false
     * otherwise.
     */
    public boolean isAttached() {
        return this.shaderProgramId != -1;
    }

    /**
     * Getter for whether this shader was destroyed.
     *
     * @return True if this shader was destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of Shader.
     *
     * @param name The name of the shader.
     * @param shaderSource The source of the shader.
     */
    public Shader(String name, String shaderSource) {
        this.name = name;
        this.uniformNames = new ArrayList<>();
        this.attributeNames = new ArrayList<>();
        this.shaderProgramId = -1;
        this.shaderId = this.init(shaderSource);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Attaches this shader to the shader program referenced by the provided ID.
     *
     * @param shaderProgramId The ID of the shader program this shader should be
     * attached to.
     */
    public void attachTo(int shaderProgramId) {
        if (this.destroyed) {
            throw new DeadShaderReferenceException(this.getName());
        }
        GL20.glAttachShader(shaderProgramId, this.shaderId);
        this.shaderProgramId = shaderProgramId;
    }

    /**
     * Detaches this shader from the shader program it was attached to, if any.
     */
    public void detach() {
        if (this.destroyed) {
            throw new DeadShaderReferenceException(this.getName());
        }
        if (this.shaderProgramId != -1) {
            GL20.glDetachShader(this.shaderProgramId, this.shaderId);
            this.shaderProgramId = -1;
        }
    }

    /**
     * Destroys this shader on the graphics card by detaching it from the shader
     * program (if there was any), and deleting the shader. After destroying
     * this shader, it's reference ID is invalid, and this instance should not
     * be reused.
     */
    public void destroy() {
        this.detach();
        GL20.glDeleteShader(this.shaderId);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Protected methods">
    /**
     * An implementation of this member should compile the shader, load it to
     * the graphics card and return its reference ID.
     *
     * @param shaderSource The source of the shader to compile and load.
     * @return The shader reference ID.
     */
    protected abstract int create(String shaderSource);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     * Calls the member
     * {@link #extractUniformNames(java.lang.String) extractUniformNames(shaderSource)},
     * and calls the subclass implementation of
     * {@link #create(java.lang.String) create(shaderSource)}.
     *
     * @param shaderSource The source of the shader that will be created.
     * @return The shader ID of this shader retrieved from the member
     * {@link #create(java.lang.String) create(shaderSource)}.
     */
    private int init(String shaderSource) {
        this.uniformNames.addAll(extractUniformNames(shaderSource));
        this.attributeNames.addAll(extractAttributeNames(shaderSource));
        return this.create(shaderSource);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Static methods">
    /**
     * Extracts all attribute names from the shader source and returns them in a
     * list.
     *
     * @param shaderSource The source of the shader that will be used to find
     * the attribute names.
     * @return A list containing all attribute names.
     */
    private static List<String> extractAttributeNames(String shaderSource) {
        return Stream.of(shaderSource.split(System.lineSeparator()))
                .filter(x -> x.contains("in "))
                .map(x -> {
                    String[] splitted = x.split(" ");
                    String withSemiColon = splitted[splitted.length - 1];
                    return withSemiColon.substring(0, withSemiColon.length() - 1);
                })
                .collect(Collectors.toList());
    }

    /**
     * Extracts all uniform variable names from the shader source and returns
     * them in a list. Uniform arrays are extracted using member
     * {@link #extractUniformArrayNames(java.lang.String) extractUniformArrayNames(uniformName)}.
     * Each element of this array will be returned with its own reference namely
     * 'arrayName[pointer]'.
     *
     * @param shaderSource The source of the shader from which the uniforms will
     * be extracted.
     * @return A list of all uniforms (and uniform array elements) found in the
     * source.
     */
    private static List<String> extractUniformNames(String shaderSource) {
        return Stream.of(shaderSource.split(System.lineSeparator()))
                .filter(x -> x.startsWith("uniform"))
                .map(x -> {
                    String[] stringArray = x.split(" ");
                    String uniformNameWithSemiColon = stringArray[stringArray.length - 1];
                    String uniformName = uniformNameWithSemiColon.substring(0, uniformNameWithSemiColon.length() - 1);
                    
                    return uniformName;
                })
                .flatMap(x -> {
                    if (x.contains("[")) {
                        return extractUniformArrayNames(x);
                    } else {
                        return Stream.of(x);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Extracts the array elements as separate uniform names. The elements of
     * this array will be returned with its own reference namely
     * 'arrayName[pointer]'.
     *
     * @param uniformName The name of the uniform array including the brackets
     * and size.
     * @return A Stream of all uniform array elements.
     */
    private static Stream<String> extractUniformArrayNames(String uniformName) {
        return Stream.of(uniformName)
                .flatMap(x -> {
                    int arraySize = Integer.valueOf(x.substring(x.indexOf("[") + 1, x.indexOf("]")));
                    String arrayName = x.substring(0, x.indexOf("["));
                    String[] uniformArray = new String[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        uniformArray[i] = arrayName + "[" + i + "]";
                    }
                    return Stream.of(uniformArray);
                });
    }
    //</editor-fold>

}
