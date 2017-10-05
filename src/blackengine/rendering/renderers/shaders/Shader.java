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
package blackengine.rendering.renderers.shaders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Blackened
 */
public abstract class Shader {

    protected int shaderId;

    private List<String> uniforms;

    public List<String> getUniforms() {
        return uniforms;
    }

    public Shader(String shaderSource) {
        this.init(shaderSource);
    }

    public final void init(String shaderSource){
        this.uniforms = extractUniforms(shaderSource);
        this.create(shaderSource);
    }

    protected abstract void create(String shaderSource);

    protected static List<String> extractUniforms(String source) {
        return Stream.of(source.split(System.lineSeparator()))
                .filter(x -> x.startsWith("uniform"))
                .flatMap(x -> {
                    if (x.contains("[")) {
                        return extractUniformArrayNames(x);
                    } else {
                        return Stream.of(x);
                    }
                })
                .collect(Collectors.toList());
    }

    protected static Stream<String> extractUniformArrayNames(String uniformName) {
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

}
