package robotrace;

import com.jogamp.opengl.glu.GLU;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
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
                setDefaultMode(gs, glu);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs, GLU glu) {
        double xCam = gs.cnt.x - (gs.vDist * sin(gs.theta));
        double yCam = gs.cnt.y - (gs.vDist * cos(gs.phi));
        double zCam = gs.vDist;
        double xCentralPoint = gs.cnt.x;
        double yCentralPoint = gs.cnt.y;
        double zCentralPoint = gs.cnt.z;
       
        glu.gluLookAt(xCam, yCam, zCam, xCentralPoint, yCentralPoint, zCentralPoint, 0.0f, 0.0f, 1.0f);

    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {

    }
}
