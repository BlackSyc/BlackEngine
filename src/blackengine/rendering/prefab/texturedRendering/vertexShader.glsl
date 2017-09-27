
#version 400 core


in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[6];


uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[6];

void main(void){

    surfaceNormal = (transformationMatrix * vec4(normal,0.0)).xyz;

    pass_textureCoords = textureCoords;
    vec4 worldPosition = transformationMatrix * vec4(position,1.0);
    for(int i=0;i<6;i++){
        toLightVector[i] = lightPosition[i] - worldPosition.xyz;
    }

    gl_Position = projectionMatrix * viewMatrix * worldPosition;

    
    
}