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
package blackengine.openGL.texture;

/**
 * An instance of this class represents a texture that is divided into multiple
 * images. The images are divided into rows and columns within the single
 * texture. Using the index property of this atlas, the renderer can know which
 * of these images should be used for rendering.
 *
 * @author Blackened
 */
public class TextureAtlas {

    /**
     * The texture that is loaded into OpenGL.
     */
    private Texture texture;

    /**
     * The number of rows in the texture atlas.
     */
    private int numberOfRows;

    /**
     * The number of columns in the texture atlas.
     */
    private int numberOfColumns;

    /**
     * The index of the selected image from the atlas.
     */
    private float index;

    /**
     * Getter for the texture.
     *
     * @return An instance of {@link blackengine.openGL.texture.Texture Texture}
     * representing a texture that is loaded into OpenGL.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Getter for the number of rows in the texture atlas.
     *
     * @return An integer representing the number of rows in the texture atlas.
     */
    public int getRows() {
        return numberOfRows;
    }

    /**
     * Getter for the number of columns in the texture atlas.
     *
     * @return An integer representing the number of columns in the texture
     * atlas.
     */
    public int getColumns() {
        return numberOfColumns;
    }

    /**
     * The index of the selected image from the atlas.
     *
     * @return A float referencing the index of the selected image. Any value
     * between whole integers represents a blend between the two images.
     */
    public float getIndex() {
        return index;
    }

    /**
     * Default constructor for creating a new texture atlas.
     *
     * @param texture The texture.
     * @param rows The number of rows in the texture atlas.
     * @param columns The number of columns in the texture atlas.
     * @param index The chosen index that will be used for rendering.
     */
    public TextureAtlas(Texture texture, int rows, int columns, float index) {
        this.texture = texture;
        this.numberOfRows = rows;
        this.numberOfColumns = columns;
        this.index = index;
    }

}
