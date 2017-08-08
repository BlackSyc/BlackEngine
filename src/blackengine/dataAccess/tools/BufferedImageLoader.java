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
