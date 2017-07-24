/* 
 * Project: Beams
 * Phase: ALPHA
 * Property of: Blackened Studio
 * Contact: blackenedstudio@gmail.com
 */
package blackengine.dataAccess.dataObjects;

/**
 *
 * @author Blackened
 */
public class MeshDataObject {
    
    private final int dimensions;

    /**
     * The positions of all vertices.
     */
    private final float[] vertexPositions;

    /**
     * The coordinates of the texture.
     */
    private final float[] textureCoords;

    /**
     * The normal vectors.
     */
    private final float[] normals;

    /**
     * The indices of the model.
     */
    private final int[] indices;
    

    public float[] getVertexPositions() {
        return vertexPositions;
    }
    
    public int getVertexCount(){
        return this.vertexPositions.length / this.dimensions;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    public MeshDataObject(float[] vertexPositions, float[] textureCoords, float[] normals, int[] indices, int dimensions) {
        this.vertexPositions = vertexPositions;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.dimensions = dimensions;
    }

}
