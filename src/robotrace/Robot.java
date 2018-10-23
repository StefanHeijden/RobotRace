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
	float[] diffuse ;
	float[] specular;
	float shininess;
	
    // This scale is used to scale the entire robot
    double[] totalScale = {1,1,1};
    public int indexRobot;
    public double runningSpeed;
    double limbMovementSpeed = 100.0;
    //body
    double[] scaleBody = {0.5,0.25,0.5};
    double heigthTorso;
    //arms and legs
    double[] scaleLimbs = {0.25,0.25,0.25};
    double limbAngle = 45.0;
    double armsAngleAmplifier = 0.5;
    //head
    double[] scaleHead = {0.3,0.3,0.3};
    double headAngle = 10.0;

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material, int index
    ) {
        this.material = material;
    	// Set material color for robot
    	diffuse = material.diffuse;
    	specular = material.specular;
    	shininess = material.shininess;
        indexRobot = index;
        runningSpeed = calcMovementSpeed();
    	// Calculate heigth of the body
    	heigthTorso = scaleLimbs[2] * 2 + scaleBody[2] / 2;
    }
    
    private double calcMovementSpeed()
    {
        if(indexRobot == 1) {
            return 0.0025;
        }
        if(indexRobot == 2) {
            return 0.0030;
        }
        if(indexRobot == 3) {
            return 0.0035;
        }
        return 0.002;
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
    	gl.glTranslated(0, 0, heigthTorso);
    	
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
    	// Translate to point where the leg needs to be drawn
    	// whether its the left or right leg depends on pos
    	gl.glTranslated(scaleLimbs[0]/2 * pos, 0, scaleLimbs[2] * 2.5);
    	
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
    	// Translate to point where the arm needs to be drawn
    	// whether its the left or right arm depends on pos
    	gl.glTranslated((scaleBody[0]/2 + scaleLimbs[0]/2) * pos, 0, heigthTorso + scaleLimbs[2]);
    	
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
    	// Translate to point where the head needs to be drawn
    	double lengthFromTorsoToNeck = (scaleBody[2] - 1)/2;
    	gl.glTranslated(0, 0, heigthTorso + lengthFromTorsoToNeck + scaleHead[2]/2);
    	
    	gl.glRotated( -90 + Math.sin(tAnim * limbMovementSpeed) * headAngle , -90, 1, 0);
    	gl.glTranslated(0, 0.5, 0);
    	
    	// Scale with scale for the head and draw a cube
    	gl.glScaled(scaleHead[0], scaleHead[1], scaleHead[2]);
    	glut.glutSolidCube(1);
    	gl.glPopMatrix();
    }
    
    private void drawBlock(GL2 gl, GLU glu, GLUT glut, float tAnim, double amplifier) {
    	// Move halfway down the length of the limb
    	gl.glTranslated(0, 0, -scaleLimbs[2] / 2);
    	
    	// Rotate the blocks of the limb based on the current tAnim
    	gl.glRotated(amplifier * Math.sin(tAnim * limbMovementSpeed) * limbAngle, -90, 1, 0);
    	
    	// Move another halfway down of the limb in the direction of the rotation
    	gl.glTranslated(0, 0, -scaleLimbs[2] / 2);
    	
    	// Scale the blocks of the limb, draw the cube and and undo the scaling for the next block
    	gl.glScaled(scaleLimbs[0], scaleLimbs[1], scaleLimbs[2]);
    	glut.glutSolidCube(1);
    	gl.glScaled(1 / scaleLimbs[0], 1 / scaleLimbs[1], 1 / scaleLimbs[2]);
    }
    
    private void drawTotal(GL2 gl, GLU glu, GLUT glut, float tAnim) {
    	// Put the robot on its position
    	gl.glTranslated(position.x, position.y, position.z);
        //Calculate the rotation for the robot
        Vector yAxis = new Vector(0,1,0); 
        //Calculate angle between Yaxis and the direction
        double angle = Math.acos(yAxis.dot(direction)/(yAxis.length() * direction.length()));
        // Turn angle from radians to degree
        angle = angle / Math.PI * 180;
        
        if(direction.x >= 0) 
        {
            gl.glRotated( angle, 0,0, 90);
        }else 
        {
            gl.glRotated( 360 - angle, 0,0, 90);
        }
        // Make the robots move back and forth a bit while running
        // Just to make animations little smoother
    	gl.glRotated(Math.sin(tAnim * limbMovementSpeed) * 15, 90, -0, 0);
    	
    	// Scale the robots
    	gl.glScaled(totalScale[0], totalScale[1], totalScale[2]);
    }
   
}
