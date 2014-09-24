package controller; 

import model.ItineraryLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IndexServlet", urlPatterns = { "/index" })
public class IndexServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        if (itinerarySelected(request)) {
            ItineraryLoader loader = new ItineraryLoader();
            loader.loadItineraryAndAllDependencies(request, response);
        } else {
            response.sendRedirect("jsp/index.jsp");
        }
    }

    private boolean itinerarySelected(final HttpServletRequest request) {
        return request.getQueryString() != null &&
                request.getQueryString().contains("itinerary_id=");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    }
}
