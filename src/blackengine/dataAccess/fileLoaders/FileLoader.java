/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.dataAccess.fileLoaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Blackened
 * @param <T> The type of data that will be retrieved from loading a file.
 */
public abstract class FileLoader<T> {
    
    protected Map<String, T> cache;

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
    protected String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    protected BufferedReader createBufferedReader(String file) {
        InputStreamReader isr = new InputStreamReader(this.createFileInputStream(file));
        return new BufferedReader(isr);
    }
    
    protected InputStream createFileInputStream(String file){
        return Class.class.getResourceAsStream(file);
    }
    
    public abstract T loadFromFile(String path, String fileName) throws IOException;

}
