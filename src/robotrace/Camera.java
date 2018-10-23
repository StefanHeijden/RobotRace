package robotrace;

import com.jogamp.opengl.glu.GLU;
import static java.lang.Math.*;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(3f, 6f, 5f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus, GLU glu) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
       /* double xCam = gs.cnt.x - (gs.vDist * sin(gs.theta));
        double yCam = gs.cnt.y - (gs.vDist * cos(gs.phi));
        double zCam = gs.vDist;*/
        
       //Spherical coordinates of the camera
        double xCam = gs.cnt.x + gs.vDist * cos(gs.theta) * cos(gs.phi);
        double yCam = gs.cnt.y + gs.vDist * sin(gs.theta) * cos(gs.phi);
        double zCam = gs.cnt.z + gs.vDist * sin(gs.phi);
        eye = new Vector(xCam, yCam, zCam);
        //Coordinates where the camera looks at
        double xCentralPoint = gs.cnt.x;
        double yCentralPoint = gs.cnt.y;
        double zCentralPoint = gs.cnt.z;
        center = new Vector(xCentralPoint, yCentralPoint, zCentralPoint);
        up = new Vector(0.0, 0.0, 1.0);
//        glu.gluLookAt(xCam, yCam, zCam, 
//                xCentralPoint, yCentralPoint, zCentralPoint, 
//                0.0, 0.0, 1.0);
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
        if (gs.tAnim > 5) {
            gs.tAnim = gs.tAnim-6;
        }     
        //focus.position is a vector which gives the position of the robot
        double posRobotx = focus.position.x;//+ gs.tAnim * 2;
        double posRoboty = focus.position.y + gs.tAnim * 2;
        double posRobotz = focus.position.z;
        
        //focus.direction is a vector which gives the direction the robot faces
        double lookRobotx = focus.direction.x + cos(gs.theta * gs.tAnim) * cos(gs.phi * gs.tAnim);
        double lookRoboty = focus.direction.y + sin(gs.theta * gs.tAnim) * cos(gs.phi * gs.tAnim);
        double lookRobotz = focus.direction.z;
        
        
        eye = new Vector(posRobotx, posRoboty, posRobotz);
        center = new Vector(lookRobotx, lookRoboty, lookRobotz);
        up= new Vector(0.0, 0.0, 1.0);
    }
}
