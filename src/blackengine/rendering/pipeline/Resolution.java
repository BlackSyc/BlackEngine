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

/**
 * Represents a resolution used for frame buffer objects, cameras and displays.
 *
 * @author Blackened
 */
public class Resolution {

    /**
     * The width of the resolution.
     */
    private final int width;

    /**
     * The height of the resolution.
     */
    private final int height;

    /**
     * Getter for the width of the resolution.
     * @return An integer representing the width of the resolution.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for the height of the resolution.
     * @return An integer representing the height of the resolution.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Default constructor for creating a new instance of Resolution.
     * @param width The width of the resolution.
     * @param height The height of the resolution.
     */
    public Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
