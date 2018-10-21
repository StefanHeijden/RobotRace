// simple vertex shader
uniform float shininess;

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = Vec4(0.5,0,0,1);
}
