/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
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
