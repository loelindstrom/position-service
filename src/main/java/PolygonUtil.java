import java.awt.geom.Point2D;
import java.util.*;

public class PolygonUtil {
    /**
     * Takes in a string with polygon coordinates and returns a list with the coordinates
     * as points. Checks that the string actually contains an even number of values, i.e. that it is
     * possible to parse them as points. TODO: Also checks that the last and the first point is the same so
     * that the polygon is closed.
     * @param coordsStr - String holding coordinates for a polygon
     * @return List with Point2D.Double where each point represent a corner of the polygon.
     */
    public static List<Point2D.Double> createPolygonCornersList(String coordsStr) {
        List<Double> coords = parseDoubles(coordsStr);
        if (coords.size() % 2 != 0) {
            throw new InputMismatchException("The string must contain an even number of doubles in order to create a list of points.");
        }

        ArrayList<Point2D.Double> polygonCorners = new ArrayList<>(5);
        double x = 0, y = 0;
        for (int i = 0; i < coords.size(); i++) {
            if (i % 2 == 0)
                x = coords.get(i);
            else {
                y = coords.get(i);
                polygonCorners.add(new Point2D.Double(x, y));
            }
        }

        //TODO: Add check to see that last point in polygon is same as first

        return polygonCorners;
    }

    /**
     * Parses all doubles in a string and returns them as doubles in a List.
     * @param str String containing the doubles (Using dot (.) as decimal))
     * @return List with the parsed doubles
     */
    private static List<Double> parseDoubles(String str) {
        ArrayList<Double> doubleList = new ArrayList<>(10);
        Scanner sc = new Scanner(str);
        sc.useLocale(Locale.ENGLISH);

        while (sc.hasNextDouble()) {
            doubleList.add(sc.nextDouble());
        }
        sc.close();

        return doubleList;
    }
}
