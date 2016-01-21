package andrey.vizualization.graph;

/**
 * Created by Andrey on 10.12.2015.
 */
public class FunctionToDraw {
    public static double valueInPoint(XYZCoord xyzCoord)
    {
        //return xyzCoord.x*xyzCoord.x+xyzCoord.y*xyzCoord.y+xyzCoord.z*xyzCoord.z;
//return Math.sin(10*(xyzCoord.x*xyzCoord.x+xyzCoord.y*xyzCoord.y))/10-xyzCoord.z+1 ;
        return Math.sin(5*xyzCoord.x) * Math.cos(5*xyzCoord.y)/5-xyzCoord.z+1;
//        return  Math.sin(xyzCoord.x)+Math.sin(xyzCoord.y)+Math.sin(xyzCoord.z)+
//                Math.cos(xyzCoord.x)+Math.cos(xyzCoord.y)+Math.cos(xyzCoord.z)-2.2+1;

    }
    public static double valueInPoint(double x, double y, double z)
    {
        return x*x+y*y+z*z;
    }
}
