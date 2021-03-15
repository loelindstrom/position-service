import java.awt.geom.Point2D;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PositionServiceServlet", urlPatterns = {"position-service"}, loadOnStartup = 1)
public class PositionServiceServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the request-parameter polygon, which shall hold coordinates for a polygon-shape
        String polyCoordsString = request.getParameter("polygon");

        // List for holding the results:
        List<String> positions;

        // If a polygon is specified, get coordinates within the polygon and put in a list:
        if (polyCoordsString != null && !polyCoordsString.isBlank()) {
            List<Point2D.Double> polyCoords;
            try {
                polyCoords = PolygonUtil.createPolygonCornersList(polyCoordsString);
            }
            catch (InputMismatchException e) {
                response.getWriter().println("There is not equally many x and y values, and therefore " +
                                             "the input cannot be converted to points.");
                return;
            }
            DataAccessLayer dal = new DataAccessLayer();
            positions = dal.getPositionsWithin(polyCoords);

        }

        // If no polygon is specified return all the positions:
        else {
            DataAccessLayer dal = new DataAccessLayer();
            positions = dal.getPositions();
        }

        // Print the psotitions:
        for (String pos : positions) {
            response.getWriter().println(pos);
        }
    }
}