/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.dataAccess.tools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Blackened
 */
public class BufferedImageLoader {



    public static BufferedImage loadBufferedImageFromFile(String path, String fileName) throws IOException {
        String extension = getFileExtension(fileName);

        switch (extension) {
            case ".png":
                return loadBufferedImageFromPNGFile(path + fileName);
            default:
                throw new UnsupportedOperationException("File extension ('" + extension + "') not (yet) supported for loading an image data object!");
        }
    }

    public static BufferedImage loadBufferedImageFromPNGFile(String file) throws IOException {

        try (InputStream inputStream = createFileInputStream(file)) {
            return ImageIO.read(inputStream);
        }
    }
    
        private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    private static InputStream createFileInputStream(String file) {
        return Class.class.getResourceAsStream(file);
    }

}
