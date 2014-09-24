package model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtilities {
    public static void forwardRequest(HttpServlet servlet,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      String target)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

    public static void forwardRequest(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String target)
            throws IOException {
        try {
            request.getRequestDispatcher(target).forward(request, response);
        } catch (ServletException ignore) {}
    }
}
