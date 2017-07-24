/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.dataAccess.fileLoaders;

import blackengine.dataAccess.dataObjects.MeshDataObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Loads mesh and animation files from different formats into memory.
 *
 * @author Blackened
 */
public class MeshLoader extends FileLoader<MeshDataObject> {
    
    private static MeshLoader instance;

    private MeshLoader() {
    }
    
    @Override
    public MeshDataObject loadFromFile(String path, String fileName) throws IOException {
        
        String extension = this.getFileExtension(fileName);

        switch (extension) {
            case ".obj":
                return this.loadMeshDataFromObjFile(path + fileName);
            case ".dae":
                return this.loadMeshFromColladaFile(path + fileName);
            default:
                throw new UnsupportedOperationException("File extension ('" + extension + "') not (yet) supported for loading a mesh data object!");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Loads a MeshDataObject from an .obj file.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private MeshDataObject loadMeshDataFromObjFile(String file) throws IOException {
        if (this.cache.containsKey(file)) {
            return this.cache.get(file);
        } else {

            try (BufferedReader reader = this.createBufferedReader(file)) {

                List<Vector3f> vertexPositions = new ArrayList<>();
                List<Vector2f> textureCoords = new ArrayList<>();
                List<Vector3f> normalVectors = new ArrayList<>();
                List<Integer> indices = new ArrayList<>();

                float[] vertexPositionArray = null;
                float[] normalVectorsArray = null;
                float[] textureCoordsArray = null;
                int[] indicesArray = null;

                String line;
                while (true) {
                    line = reader.readLine();
                    String[] currentLine = line.split(" ");
                    if (line.startsWith("v ")) {
                        Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                        vertexPositions.add(vertex);
                    } else if (line.startsWith("vt ")) {
                        Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                        textureCoords.add(texture);
                    } else if (line.startsWith("vn ")) {
                        Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                        normalVectors.add(normal);
                    } else if (line.startsWith("f ")) {
                        textureCoordsArray = new float[vertexPositions.size() * 2];
                        normalVectorsArray = new float[vertexPositions.size() * 3];
                        break;
                    }
                }
                while (line != null) {
                    if (!line.startsWith("f ")) {
                        line = reader.readLine();
                        continue;
                    }
                    String[] currentLine = line.split(" ");
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    processVertex(vertex1, indices, textureCoords, normalVectors, textureCoordsArray, normalVectorsArray);
                    processVertex(vertex2, indices, textureCoords, normalVectors, textureCoordsArray, normalVectorsArray);
                    processVertex(vertex3, indices, textureCoords, normalVectors, textureCoordsArray, normalVectorsArray);
                    line = reader.readLine();
                }
                reader.close();

                vertexPositionArray = new float[vertexPositions.size() * 3];
                indicesArray = new int[indices.size()];

                int vertexPointer = 0;
                for (Vector3f vertex : vertexPositions) {
                    vertexPositionArray[vertexPointer++] = vertex.x;
                    vertexPositionArray[vertexPointer++] = vertex.y;
                    vertexPositionArray[vertexPointer++] = vertex.z;
                }

                for (int i = 0; i < indices.size(); i++) {
                    indicesArray[i] = indices.get(i);
                }

                MeshDataObject meshDataObject = new MeshDataObject(vertexPositionArray, textureCoordsArray, normalVectorsArray, indicesArray, 3);
                this.cache.put(file, meshDataObject);
                return meshDataObject;

            } catch (IOException e) {
                throw new IOException("obj file could not be loaded");
            }
        }

    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }

    private MeshDataObject loadMeshFromColladaFile(String file) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }
    
    //</editor-fold>
    
    
    public static MeshLoader getInstance(){
        if(instance == null){
            instance = new MeshLoader();
        }
        return instance;
    }
    

}
