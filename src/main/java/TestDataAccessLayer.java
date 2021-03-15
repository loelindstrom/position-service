import java.awt.geom.Point2D;
import java.util.*;

public class TestDataAccessLayer {
    public static void main(String[] args) {
        DataAccessLayer dal = new DataAccessLayer();

        // Gets all points in the database:
        System.out.println("All points:");
        List<String> positions = dal.getPositions();
        for (String pos: positions) {
            System.out.println(pos);
        }
        System.out.println();


        // Gets all points in the database within the specified polygon:
        // Smaller size:
        // String polyCoords = "57.64 11.8820 57.64 12.12 57.73 12.12 57.73 11.8820 57.64 11.8820"
        // Bigger size:
        // String polyCoords = "56.64 10.8820 56.64 12.12 57.73 12.12 57.73 10.8820 56.64 10.8820";
        String polyCoords = "56.64 10.8820 56.64 12.12 57.73 12.12 57.73 10.8820 56.64 10.8820";
        List<Point2D.Double> polygonCorners = PolygonUtil.createPolygonCornersList(polyCoords);
        System.out.println("Points within:");
        List<String> positionsWithin = dal.getPositionsWithin(polygonCorners);
        for (String pos: positionsWithin) {
            System.out.println(pos);
        }
        System.out.println();
    }
}
