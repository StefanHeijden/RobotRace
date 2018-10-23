
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    @Override
    protected Vector getPoint(double t) {
    	double x = Math.cos(2 * Math.PI * t);
    	double y = Math.sin(2 * Math.PI * t);
    	double z = 1;
        return new Vector(x,y,z);
    }

    @Override
    protected Vector getTangent(double t) {
    	double x = Math.sin(2 * Math.PI * t);
    	double y = Math.cos(2 * Math.PI * t);
    	double z = 0;
    	Vector v = new Vector(x,y,z);
    	v = v.normalized();
    	return v;

    }
    
}
