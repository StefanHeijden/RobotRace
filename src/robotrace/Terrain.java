package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        drawFieldOne(gl, glu, glut);
        drawFieldTwo(gl, glu, glut);
        drawFieldThree(gl, glu, glut);
        drawFieldFour(gl, glu, glut);
        drawFieldFive(gl, glu, glut);
        
    }
    
    public void drawFieldOne(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslated(10, -10, 0);
        gl.glScaled(20, 20, 0);
        gl.glPopMatrix();
    }
    
    public void drawFieldTwo(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslated(-10, -10, 0);
        gl.glScaled(20, 20, 0);
        gl.glPopMatrix();
    }
    
    public void drawFieldThree(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslated(-10, 10, 0);
        gl.glScaled(20, 20, 0);
        gl.glPopMatrix();
    }
    
    public void drawFieldFour(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslated(5, 10, 0);
        gl.glScaled(10, 20, 0);
        gl.glPopMatrix();
    }
    
    public void drawFieldFive(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslated(15, 10, 0);
        gl.glScaled(10, 20, 0);
        gl.glPopMatrix();
    }
}
