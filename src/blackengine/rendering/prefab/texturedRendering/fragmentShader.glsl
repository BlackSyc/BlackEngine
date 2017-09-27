#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[6];

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform vec3 lightColour[6];
uniform vec3 lightPosition[6];
uniform vec3 lightAttenuation[6];

// Calculates the total diffuse lighting per fragment.
vec3 calculateDiffuseLighting(void){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 totalDiffuse = vec3(0.0);

    for(int i=0; i<6; i++){
        float distanceToLight = length(toLightVector[i]);
        float attFactor = lightAttenuation[i].x + (lightAttenuation[i].y * distanceToLight) + (lightAttenuation[i].z * distanceToLight * distanceToLight);

        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1,0.8);
        totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
    }
    return totalDiffuse;
}


void main(void){
    vec4 textureVec = texture(textureSampler, pass_textureCoords);

    vec3 totalDiffuse = calculateDiffuseLighting();
    
    out_Colour = vec4(totalDiffuse,1.0) * textureVec;
}
