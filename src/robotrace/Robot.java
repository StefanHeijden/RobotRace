package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    // This scale is used to scale the entire robot
    int[] totalScale = {1,1,1};
    int[] startPosition = {-8,-20,0};
    double runningSpeed = 0.1;
    int distanceBetweenRobots = 4;
    //body
    int[] scaleBody = {2,1,2};
    //arms
    double[] scaleLimbs = {1,1,1};
    double limbMovementSpeed = 0.1;
    double limbAngle = 45.0;
    double armsAngleAmplifier = 0.5;
    //legs
    //head

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material, int startPosition
            
    ) {
        this.material = material;
        this.startPosition[0] += startPosition * distanceBetweenRobots;
        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
    	// Draw each body part separate. start with the body
        drawBody(gl, glu, glut, tAnim);
        // Draw the right leg
        drawLeg(gl, glu, glut,tAnim, 1);
        // Draw the left leg
        drawLeg(gl, glu, glut,tAnim, -1);
        // Draw the right arm
        drawArm(gl, glu, glut,tAnim, 1);
        // Draw the left arm
        drawArm(gl, glu, glut,tAnim, -1);
        // Draw the head
        drawHead(gl, glu, glut,tAnim);
    }
    
    private void drawBody(GL2 gl, GLU glu, GLUT glut, float tAnim) {
    	gl.glPushMatrix(); 
    	// Add Matrix for the total of the robot
    	drawTotal(gl, glu, glut,tAnim);
    	
    	// Start drawing from the heigth of the legs
    	gl.glTranslated(0, 0, 1);
    	
    	// Scale so that the drawn cube looks like a body
        gl.glScaled(scaleBody[0], scaleBody[1], scaleBody[2]);
        
        // Draw the cube
        glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    private void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, double pos) {
    	gl.glPushMatrix(); 
    	// Add Matrix for the total of the robot
    	drawTotal(gl, glu, glut,tAnim);
    	gl.glColor3d(0.5, 0, 0);
    	// Translate to point where the leg needs to be drawn
    	// whether its the left or right leg depends on pos
    	gl.glTranslated(0.5 * pos, 0, 0.5);
    	
    	// Draw first block of the leg
    	drawBlock(gl, glu, glut,tAnim, pos);
        
        // Draw second block of the leg
        drawBlock(gl, glu, glut,tAnim, pos);
        gl.glPopMatrix();
    }
    
    private void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim, double pos) {
    	gl.glPushMatrix();
    	// Add Matrix for the total of the robot
    	drawTotal(gl, glu, glut,tAnim);
    	gl.glColor3d(0, 0.5, 0);
    	// Translate to point where the arm needs to be drawn
    	// whether its the left or right arm depends on pos
    	gl.glTranslated(1.5 * pos, 0, 2.5);
    	
    	// Draw first block of the arm. -pos is used since this makes the left arm move the
    	// same as the right leg and vice versa, creating a more natural running animation
    	drawBlock(gl, glu, glut,tAnim, -pos * armsAngleAmplifier);
        
        // Draw second block of the arm, again -pos is used
        drawBlock(gl, glu, glut,tAnim, -pos * armsAngleAmplifier);
        gl.glPopMatrix();
    }
    
    private void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim) {
    	gl.glPushMatrix(); 
    	// Add Matrix for the total of the robot
    	drawTotal(gl, glu, glut,tAnim);
    	gl.glColor3d(0, 0, 0.5);
    	// Translate to point where the head needs to be drawn
    	gl.glTranslated(0, 0, 2.2);
    	gl.glRotated( Math.sin(tAnim * 0.1) * 45 - 100, -90, 1, 0);
    	gl.glTranslated(0, 0.5, 0);
    	gl.glScaled(1.2, 1.2, 1.2);
    	glut.glutSolidCube(1);
    	gl.glPopMatrix();
    }
    
    private void drawBlock(GL2 gl, GLU glu, GLUT glut, float tAnim, double amplifier) {
    	// Move halfway down the length of the limb
    	gl.glTranslated(0, 0, -scaleLimbs[2] / 2);
    	
    	// Rotate the blocks of the limb based on the current tAnim
    	gl.glRotated(amplifier * Math.sin(tAnim * limbMovementSpeed) * limbAngle, -90, 1, 0);
    	
    	// Move another halfway down the length of the limb. why?
    	gl.glTranslated(0, 0, -scaleLimbs[2] / 2);
    	
    	// Scale the blocks of the limb, draw the cube and and undo the scaling for the next block
    	gl.glScaled(scaleLimbs[0], scaleLimbs[1], scaleLimbs[2]);
    	glut.glutSolidCube(1);
    	gl.glScaled(1 / scaleLimbs[0], 1 / scaleLimbs[1], 1 / scaleLimbs[2]);
    }
    
    private void drawTotal(GL2 gl, GLU glu, GLUT glut, float tAnim) {
    	// Put the robot on its start position
    	gl.glTranslated(startPosition[0], startPosition[1], startPosition[2]);
    	
    	// Move the robots based on the animation and track
    	gl.glTranslated(0, tAnim * runningSpeed, 0);
    	//gl.glRotated(0.5 * -90.0, 0, 1, 0);
    	
    	// Scale the robots
    	gl.glScaled(totalScale[0], totalScale[1], totalScale[2]);
    }
    
}
