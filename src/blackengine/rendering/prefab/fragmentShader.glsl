#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;

void main(void){

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    vec4 textureVec = texture(textureSampler, pass_textureCoords);

    //if(textureVec.a<1){
    //    discard;
    //}

    for(int i=0; i<4; i++){

        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
        
        if(attFactor < 3 && attFactor > 0){

            //DIFFUSE LIGHTING
            vec3 unitLightVector = normalize(toLightVector[i]);
            float nDot1 = dot(unitNormal, unitLightVector);
            float brightness = max(nDot1,0.0);
            totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;

            //SPECULAR LIGHTING
            vec3 lightDirection = -unitLightVector;
            vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
            float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
            specularFactor = max(specularFactor, 0.0);
            float dampedFactor = pow(specularFactor, shineDamper);
            totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i])/attFactor;
        
        }
        
    }
    totalDiffuse = max(totalDiffuse, 0.2);

    
    out_Colour = vec4(totalDiffuse,1.0) * textureVec + vec4(totalSpecular, 0);
    //out_Colour = textureVec;

    
}