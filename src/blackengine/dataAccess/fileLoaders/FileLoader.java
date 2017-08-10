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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class providing core functionality for loading in new files and
 * caching them.
 *
 * @author Blackened
 * @param <T> The type of data that will be retrieved from loading a file.
 */
public abstract class FileLoader<T> {

    /**
     * A local cache of all loaded files mapped to their path.
     */
    protected Map<String, T> cache;

    /**
     * Default constructor.
     */
    public FileLoader() {
        this.cache = new HashMap<>();
    }

    /**
     * Retrieves the file extension from a given file in a String format.
     *
     * @param fileName The file name for which the extension must be retrieved.
     * @return The file extension of the given file in a String format
     * (including the dot).
     */
    protected final String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    /**
     * Creates an instance of {@link java.io.BufferedReader BufferedReader} from
     * a file path.
     *
     * @param filePath The file path that will be used to create a buffered
     * reader object.
     * @return An instance of {@link java.io.BufferedReader BufferedReader}
     * referencing the specified file path.
     */
    protected BufferedReader createBufferedReader(String filePath) {
        InputStreamReader isr = new InputStreamReader(this.createFileInputStream(filePath));
        return new BufferedReader(isr);
    }

    /**
     * Creates an instance of {@link java.io.InputStream InputStream} from a
     * file path.
     *
     * @param filePath The file path that will be used to create an input stream
     * object.
     * @return An instance of {@link java.io.InputStream InputStream}
     * referencing the specified file path.
     */
    protected final InputStream createFileInputStream(String filePath) {
        return Class.class.getResourceAsStream(filePath);
    }

    /**
     * An implementation of this method should load in data object of type T
     * from a file path.
     *
     * @param filePath The file path used to load in the data that will be
     * represented in the data object.
     * @return An instance of T containing the data loaded in from the file.
     * @throws IOException throws an IO Exception when the file could not be
     * read.
     */
    public abstract T loadFromFile(String filePath) throws IOException;

}
