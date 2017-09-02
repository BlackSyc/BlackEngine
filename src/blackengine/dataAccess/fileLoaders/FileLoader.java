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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    protected BufferedReader createBufferedReader(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);
        return new BufferedReader(isr);
    }

    protected final InputStream createResourceInputStream(String resourcePath) {
        return Class.class.getResourceAsStream(resourcePath);
    }

    protected final InputStream createFileInputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }

    /**
     * An implementation of this method should do all logic necessary for
     * specifically loading a file to an instance of T.
     *
     * @param inputStream The input stream that can be used to read data from
     * the file or resource.
     * @param extension The extension the file or resource has.
     * @return
     * @throws IOException
     */
    protected abstract T loadData(InputStream inputStream, String extension) throws IOException;

    /**
     * Checks its internal cache for previously loaded resources, otherwise
     * creates an input stream and calls {@link #loadData(java.io.InputStream, java.lang.String) loadData(...).
     *
     * @param resourcePath
     * @return
     * @throws IOException
     */
    public final T loadResource(String resourcePath) throws IOException {
        if (this.cache.containsKey(resourcePath)) {
            return this.cache.get(resourcePath);
        }
        T data = this.loadData(this.createResourceInputStream(resourcePath), this.getFileExtension(resourcePath));
        this.cache.put(resourcePath, data);
        return data;
    }

    /**
     * Checks its internal cache for previously loaded files, otherwise
     * creates an input stream and calls {@link #loadData(java.io.InputStream, java.lang.String) loadData(...).
     *
     * @param filePath The <b> absolute </b> path to the file.
     * @return
     * @throws IOException
     */
    public final T loadFile(String filePath) throws IOException {
        if (this.cache.containsKey(filePath)) {
            return this.cache.get(filePath);
        }
        T data = this.loadData(this.createFileInputStream(filePath), this.getFileExtension(filePath));
        this.cache.put(filePath, data);
        return data;

    }

    /**
     * Checks its internal cache for previously loaded files, otherwise
     * creates an input stream and calls {@link #loadData(java.io.InputStream, java.lang.String) loadData(...).
    
     * @param file The file to be loaded.
     * @return
     * @throws IOException 
     */
    public final T loadFile(File file) throws IOException {
        return this.loadFile(file.getAbsolutePath());
    }

}
