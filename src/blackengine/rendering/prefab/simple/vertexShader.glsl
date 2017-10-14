#version 400 core

// The position of the current vertex.
in vec3 position;
// The texture coordinates of the current vertex.
in vec2 textureCoords;

// The texture coordinates for the current vertex.
out vec2 pass_textureCoords;

// The transformation matrix for the current vertex (retrieved from the Transform of the entity).
uniform mat4 transformationMatrix;

// The view matrix that will be used to determine the position of the vertex on screen (retrieved from the camera).
uniform mat4 viewMatrix;

// The projection matrix that will be used. This is set once in the Render Engine and is determined by the near plane and far plane configuration.
uniform mat4 projectionMatrix;

// The main method that processes each vertex
void main(void){

    // The texture coordinates stay as they were defined in the Vao.
    pass_textureCoords = textureCoords;

    // The world position of the vertex is calculated by multiplying the relative position of the vertex (to its origin) by the transformation.
    vec4 worldPosition = transformationMatrix * vec4(position,1.0);

    // The final position on screen is calculated by multiplying the worldPosition by the viewmatrix and projection matrix (ORDER MATTERS HERE).
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
}
