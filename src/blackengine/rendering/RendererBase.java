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
package blackengine.rendering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Blackened
 */
public abstract class RendererBase {

    //<editor-fold defaultstate="collapsed" desc="Properties">
    /**
     * The ID of the shader program.
     */
    private int programID;

    /**
     * The ID of the vertex shader.
     */
    private int vertexShaderID;

    /**
     * The ID of the fragment shader.
     */
    private int fragmentShaderID;

    /**
     * A map of all single (e.g. no arrays) uniform variable locations mapped to
     * their name.
     */
    private Map<String, Integer> uniformLocations = new HashMap<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor for creating a new instance of RenderBase. Loads the
     * vertex shader and fragment shader to OpenGL, binds the attributes and
     * retrieves uniform variable locations.
     *
     * @param vertexFile
     * @param fragmentFile
     */
    public RendererBase(String vertexFile, String fragmentFile) {
        this.load(vertexFile, fragmentFile);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Starts the program.
     */
    public final void start() {
        GL20.glUseProgram(programID);
    }

    /**
     * Stops the program.
     */
    public final void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Destroys the shader program and all its shaders.
     */
    public void destroy() {
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    public void initialize() {

    }

    /**
     * Bind all attributes of the shaders.
     */
    public abstract void bindAttributes();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Protected Methods">
    /**
     * Will take in the number of the attribute list in the VAO and bind it to
     * the variable name in the shader.
     *
     * @param attribute The number of the attribute list in the VAO.
     * @param variableName The name of the variable in the shader.
     */
    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
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
    protected void loadUniformMatrix(String uniformName, Matrix4f matrix) {
        int uniformLocation = this.getUniformLocation(uniformName);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(uniformLocation, false, matrixBuffer);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Loads the shader program with its shaders to OpenGL, and calls the
     * bindAttributes method.
     */
    private void load(String vertexFile, String fragmentFile) {
        List<String> uniformVariables = new ArrayList<>();
        uniformVariables.addAll(this.loadShader(vertexFile, GL20.GL_VERTEX_SHADER));
        uniformVariables.addAll(this.loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER));

        this.programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        this.start();
        this.loadUniformLocations(uniformVariables);
        this.stop();
    }

    /**
     * Retrieves all uniform locations.
     *
     * @param uniforms
     */
    private void loadUniformLocations(List<String> uniformVariables) {

        uniformVariables.forEach(x -> {
            if (x.contains("[")) {
                this.loadUniformArrayLocations(x);
            } else {
                this.uniformLocations.put(x, this.getUniformLocation(x));
            }
        });

    }

    /**
     * Retrieves the uniform locations for all elements of the array variable.
     *
     * @param uniformArrayVariable
     */
    private void loadUniformArrayLocations(String uniformArrayVariable) {
        int arraySize = Integer.valueOf(uniformArrayVariable
                .substring(uniformArrayVariable.indexOf("[") + 1,
                        uniformArrayVariable.indexOf("]")));
        String rawVariableName = uniformArrayVariable.substring(0, uniformArrayVariable.indexOf("["));
        String specifiedVariableName = "";
        for (int i = 0; i < arraySize; i++) {
            specifiedVariableName = rawVariableName + "[" + i + "]";
            this.uniformLocations.put(specifiedVariableName, this.getUniformLocation(specifiedVariableName));
        }
    }

    /**
     * Get the location for a uniform variable.
     *
     * @param uniformName The name of the uniform variable for which the
     * location will be returned.
     * @return The location of a uniform variable specified by the name.
     */
    private int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    /**
     * Loads a shader to OpenGL and retrieves its uniform variables.
     *
     * @param shaderFile
     * @return
     */
    private List<String> loadShader(String shaderFile, int shaderType) {
        List<String> uniformVariables = new ArrayList<>();
        StringBuilder shaderSource = new StringBuilder();

        try (InputStream is = Class.class.getResourceAsStream(shaderFile)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
                if (line.startsWith("uniform")) {
                    String[] uniformInformation = line.split(" ");
                    uniformVariables.add(uniformInformation[2].replace(";", ""));
                }
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(RendererBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        int shaderID = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println("Shader '" + shaderFile + "' did not compile! Compile Status: " + GL20.GL_COMPILE_STATUS);
        }
        switch (shaderType) {
            case GL20.GL_VERTEX_SHADER:
                this.vertexShaderID = shaderID;
                break;
            case GL20.GL_FRAGMENT_SHADER:
                this.fragmentShaderID = shaderID;
                break;
            default:
                System.out.println("Shadertype is not implemented yet!");
                break;
        }
        return uniformVariables;
    }
    //</editor-fold>

}
