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
    protected ImageDataObject loadData(InputStream inputStream, String extension) throws IOException{
        switch (extension) {
            case ".png":
                return this.loadImageDataFromPNG(inputStream);
            default:
                throw new UnsupportedOperationException("File extension ('" + extension + "') not (yet) supported for loading an image data object!");
        }
    }

    private ImageDataObject loadImageDataFromPNG(InputStream inputStream) throws IOException {

        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;

        PNGDecoder decoder = new PNGDecoder(inputStream);

        width = decoder.getWidth();
        height = decoder.getHeight();
        buffer = ByteBuffer.allocateDirect(4 * width * height);
        decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
        buffer.flip();

        return new ImageDataObject(buffer, width, height);

    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

}
