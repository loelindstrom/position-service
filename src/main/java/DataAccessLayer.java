import java.awt.geom.Point2D;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataAccessLayer {
    private String user = "root";
    private String password = "pass";
    private String dbURL = "jdbc:mysql://localhost:3306/spatial_trial";
    private String positionCol = "car_pos";
    private String tableName = "car";

    public List<String> getPositions() {
        // List for returning the results:
        LinkedList<String> positions = new LinkedList<>();

        // Connect to database (Correct driver is inferred due to "mysql-connector-java"-dependency specified in build.gradle)
        try (Connection con = DriverManager.getConnection(dbURL, user, password)) {

            // Query to execute:
            String query = String.format("SELECT ST_AsText(%s) " +
                                           "FROM %s", positionCol, tableName);

            // Create a statemnet with the prepared statement template:
            try (Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

                // Fetch results and put into list:
                try (ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        positions.add(rs.getString(1));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return positions;
    }

    public List<String> getPositionsWithin(List<Point2D.Double> polygonCorners) {
        // List for returning the results:
        LinkedList<String> positions = new LinkedList<>();

        // Create a string from the points
        String mysqlPolyString = createPolygonString(polygonCorners);

        // The prepared statement with a parameter for the poly corners in text form:
        String preStmt = String.format("SELECT ST_AsText(%s) FROM car " +
                                       "WHERE MBRContains(ST_PolyFromText(?, 4326), %s)",
                                        positionCol, positionCol);

        // Connect to database (Correct driver is inferred due to "mysql-connector-java"-dependency specified in build.gradle)
        try (Connection con = DriverManager.getConnection(dbURL, user, password)) {

            // Create a statement from the prepared statement-string:
            try (PreparedStatement stmt = con.prepareStatement(preStmt)) {

                // Set the parameter of the prep. statement
                stmt.setString(1, mysqlPolyString);

                // Fetch and print the results if there are any found:
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        positions.add(rs.getString(1));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return positions;
    }

    private String createPolygonString(List<Point2D.Double> polygonCorners) {
        // Put the corner-points into a MySQL Polygon-string
        // (To be a polygon the first and last point must be the same):
        StringBuilder mysqlPoly = new StringBuilder("POLYGON(())");
        for (Point2D.Double pt : polygonCorners) {
            mysqlPoly.insert(9, pt.getX() + " " + pt.getY() + ", ");
        }
        mysqlPoly.delete(mysqlPoly.length() - 4, mysqlPoly.length() - 2);

        return mysqlPoly.toString();
    }
}
