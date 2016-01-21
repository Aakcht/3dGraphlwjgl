package andrey.vizualization.graph;

import java.util.*;

/**
 * Created by Andrey on 10.12.2015.
 */
public class AllTriangles {
     //smallcubesize
    double border;

    AllTriangles(double brdr)
    {
        border = brdr;
    }

    public static List<Triangle> findAllTriangles(int pointCount)
    {
        List<Triangle> allTriangles= new LinkedList<>();
        double dist = 2.0/pointCount;
        for (int i = 0; i < pointCount ; i++)
            for (int j = 0; j < pointCount ; j++)
                for (int k = 0; k < pointCount; k++)
                {
                    GridCell gridCell = new GridCell(new XYZCoord(-1.0+dist/2+dist*i, -1.0+dist/2+dist*j, -1.0+dist/2+dist*k ), dist);
                    allTriangles.addAll(GridCell.Polygonise(gridCell,1.0));
                }

        return allTriangles;
    }

}
