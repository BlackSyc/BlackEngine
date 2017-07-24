/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.dataAccess.fileLoaders;

import blackengine.dataAccess.dataObjects.ImageDataObject;
import de.matthiasmann.twl.utils.PNGDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author Blackened
 */
public class ImageLoader extends FileLoader<ImageDataObject> {

    private static ImageLoader instance;

    private ImageLoader() {
    }

    @Override
    public ImageDataObject loadFromFile(String path, String fileName) throws IOException {
        String extension = this.getFileExtension(fileName);

        switch (extension) {
            case ".png":
                return this.loadImageDataFromPNGFile(path + fileName);
            default:
                throw new UnsupportedOperationException("File extension ('" + extension + "') not (yet) supported for loading an image data object!");
        }
    }

    private ImageDataObject loadImageDataFromPNGFile(String file) throws IOException {
        if (this.cache.containsKey(file)) {
            return this.cache.get(file);
        } else {
            System.out.println("loading " + file);
            int width = 0;
            int height = 0;
            ByteBuffer buffer = null;

            try (InputStream inputStream = this.createFileInputStream(file)) {

                PNGDecoder decoder = new PNGDecoder(inputStream);

                width = decoder.getWidth();
                height = decoder.getHeight();
                buffer = ByteBuffer.allocateDirect(4 * width * height);
                decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
                buffer.flip();
                inputStream.close();
                
                ImageDataObject imageDataObject = new ImageDataObject(buffer, width, height);
                this.cache.put(file, imageDataObject);
                return imageDataObject;
            } 
        }
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }
}
