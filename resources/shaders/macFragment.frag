#version 330 core

in vec2 texCoord0;
uniform vec3 color;
uniform sampler2D sampler;
out vec4 fragColor;

void main( ){
  vec4 textureColor = texture( sampler, texCoord0.xy );
  vec4 emptyVector = vec4(0.0,0.0,0.0,0.0);
	if( textureColor == emptyVector ){
	  fragColor = vec4( color, 1 );
	} else {
	  fragColor = textureColor * vec4( color, 0 );
	}
}
