#version 400 core

in vec2 pass_textureCoords;

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform bool textured;


void sampleTexture(void){
    out_Colour = texture(textureSampler, pass_textureCoords);
}

void sampleColour(void){
    out_Colour = vec4(0,0.5,1,1);
}

void main(void){
    
    
    if(textured){
        sampleTexture();
    }
    else{
        sampleColour();
    }
    

    
}