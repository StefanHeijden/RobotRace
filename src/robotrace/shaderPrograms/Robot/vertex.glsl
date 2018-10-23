// simple vertex shader
#version 120

uniform float diffuseX;
uniform float diffuseY;
uniform float diffuseZ;
uniform float specularX;
uniform float specularY;
uniform float specularZ;
uniform float shininess;

vec4 shading(vec3 P, vec3 N) {
    vec4 result = vec4(0,0,0,1); // opaque black
    vec4 diffuse = vec4(diffuseX, diffuseY, diffuseZ, 1.0f );
    vec4 specular = vec4(specularX, specularY, specularZ, 1.0f );
    gl_LightSourceParameters light = gl_LightSource[0];

    // compute diffuse contribution
    vec3 L = normalize(light.position.xyz - P); // vector towards light source
    vec4 Idiff = light.diffuse * diffuse * max(dot(N,L), 0.0);
    result += clamp(Idiff, 0.0, 1.0); 

    // compute specular contribution
    vec3 E = normalize(-reflect(L,N)); // position of camera in View space
    vec3 V = normalize(-P);  // direction towards viewer
    vec4 Ispec = light.specular * specular
         * pow(max(dot(V,E),0.0),0.3 * shininess);
    result += clamp(Ispec, 0.0, 1.0);

    return result;
}
void main() {
	vec3 N = gl_NormalMatrix * gl_Normal;
	vec4 O = gl_ModelViewMatrix * gl_Vertex;
	vec3 P = O.xyz / O.w;
	// output of vertex shader
	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_FrontColor = shading(P, N);
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}