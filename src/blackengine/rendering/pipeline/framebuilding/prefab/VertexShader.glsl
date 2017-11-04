#version 400 core

// The position of the current vertex.
in vec3 position;
// The texture coordinates of the current vertex.
in vec2 textureCoords;

// The texture coordinates for the current vertex.
out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;

// The main method that processes each vertex
void main(void){

    // The texture coordinates stay as they were defined in the Vao.
    pass_textureCoords = vec2(textureCoords.x, 1-textureCoords.y);

    // The final position on screen is calculated by multiplying the worldPosition by the viewmatrix and projection matrix (ORDER MATTERS HERE).
    gl_Position = transformationMatrix * vec4(position,1.0);
}


