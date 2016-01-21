package andrey.vizualization.graph;

/**
 * Created by Andrey on 10.12.2015.
 */
public class XYZCoord {

    public double x,y,z;
    XYZCoord(){
        x=0;
        y=0;
        z=0;
    }

    XYZCoord(double x1, double y1, double z1)
    {
        x=x1;
        y=y1;
        z=z1;
    }


        public boolean equals(Object obj)
        {
            return obj instanceof XYZCoord &&
                    ((this.x-((XYZCoord) obj).x)*(this.x-((XYZCoord) obj).x) +
                            (this.y-((XYZCoord) obj).y)*(this.y-((XYZCoord) obj).y) +
                            (this.z-((XYZCoord) obj).z)*(this.z-((XYZCoord) obj).z)< 0.00001);
        }
    }


