package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

public class BrowserErrorHandling {
    public static final void printErrorToBrowser(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Exception ex) {
        try {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            request.setAttribute("STACK_TRACE", sw.toString());
            request.getRequestDispatcher("jsp/errorStackTrace.jsp")
                    .forward(request, response);
        } catch (Exception ignore) {}
    }

    public static final void printErrorToBrowser(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 String message) {
        try {
            request.setAttribute("STACK_TRACE", message);
            request.getRequestDispatcher("jsp/errorStackTrace.jsp")
                    .forward(request, response);
        } catch (Exception ignore) {}
    }
}
