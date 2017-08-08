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
package blackengine.dataAccess.dataObjects;

import java.nio.ByteBuffer;

/**
 * An instance of this class represents an image on disk, loaded in through the
 * ImageLoader. An instance of this class is immutable.
 *
 * @author Blackened
 */
public class ImageDataObject {

    /**
     * The width of the image in pixels.
     */
    private final int width;

    /**
     * The height of the image in pixels.
     */
    private final int height;

    /**
     * The byte buffer containing all image data.
     */
    private final ByteBuffer buffer;

    /**
     * Getter for the width.
     *
     * @return The width of the image in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for the height.
     *
     * @return The height of the image in pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter for the buffer.
     *
     * @return The byte buffer containing all image data.
     */
    public ByteBuffer getBuffer() {
        return buffer;
    }

    /**
     * Default constructor for creating a new instance of ImageDataObject.
     *
     * @param buffer The byte buffer representing the image data.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public ImageDataObject(ByteBuffer buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }
}
