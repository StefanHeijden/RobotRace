// simple vertex shader 
#version 120

vec4 shading(vec3 P, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
	vec4 result = vec4(0,0,0,1); // opaque black
	result += light.ambient * mat.ambient; 

	// compute diffuse contribution
	vec3 L = normalize(gl_LightSource[0].position.xyz - P); // vector towards light source
	vec4 Idiff = light.diffuse * mat.diffuse * max(dot(N,L), 0.0);
	result += clamp(Idiff, 0.0, 1.0); 

	vec3 E = normalize(-reflect(L,N)); // position of camera in View space
	vec3 V = normalize(-P); // direction towards viewer
	// compute specular contribution

	vec4 Ispec = light.specular * mat.specular
		* pow(max(dot(V,E),0.0),0.3*gl_FrontMaterial.shininess);
	result += clamp(Ispec, 0.0, 1.0); 

	return result;
}

void main() {
	// pick up light LIGHT0 and material properties set in ShaderMaker
	gl_LightSourceParameters light = gl_LightSource[0];
	gl_MaterialParameters mat = gl_FrontMaterial;
	vec3 N = gl_NormalMatrix * gl_Normal; // TODO: transform normal vector to view space
	vec4 O = gl_ModelViewMatrix * gl_Vertex;
	vec3 P = O.xyz / O.w; // TODO: compute vertex position in 3-D view coordinates
	// output of vertex shader
	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_FrontColor = vec4(0.5, 0, 0, 1); // shading(P, N, light, mat)
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}