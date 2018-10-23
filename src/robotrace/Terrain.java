package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        double height = 0.6 * cos(0.3 * x + 0.2 * y) + 0.4 * cos(x - 0.5 * y);
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, double height) {
        drawFieldOne(gl, glu, glut, height);
        drawFieldTwo(gl, glu, glut, height);
        drawFieldThree(gl, glu, glut, height);
        drawFieldFour(gl, glu, glut, height);
        drawFieldFive(gl, glu, glut, height);
        
    }
    
    public void drawFieldOne(GL2 gl, GLU glu, GLUT glut, double height) {
        gl.glPushMatrix();
        gl.glTranslated(10, -10, 0);
        gl.glScaled(20, 20, height);
        gl.glPopMatrix();
    }
    
    public void drawFieldTwo(GL2 gl, GLU glu, GLUT glut, double height) {
        gl.glPushMatrix();
        gl.glTranslated(-10, -10, 0);
        gl.glScaled(20, 20, height);
        gl.glPopMatrix();
    }
    
    public void drawFieldThree(GL2 gl, GLU glu, GLUT glut, double height) {
        gl.glPushMatrix();
        gl.glTranslated(-10, 10, 0);
        gl.glScaled(20, 20, height);
        gl.glPopMatrix();
    }
    
    public void drawFieldFour(GL2 gl, GLU glu, GLUT glut, double height) {
        gl.glPushMatrix();
        gl.glTranslated(5, 10, 0);
        gl.glScaled(10, 20, height);
        gl.glPopMatrix();
    }
    
    public void drawFieldFive(GL2 gl, GLU glu, GLUT glut, double height) {
        gl.glPushMatrix();
        gl.glTranslated(15, 10, 0);
        gl.glScaled(10, 20, height);
        gl.glPopMatrix();
    }
}
