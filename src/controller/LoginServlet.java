package controller;

import static model.ServletUtilities.*;
import model.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {""})
public class LoginServlet extends HttpServlet {
    private LoginForm loginForm = new LoginForm();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        redirectToLoginPage(response);
    }

    private void redirectToLoginPage(HttpServletResponse response)
        throws IOException {
        response.sendRedirect("jsp/index.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        if (loginForm.isAuthenticationSuccessful(request)) {
            response.sendRedirect("jsp/createLoginSession.jsp");
        } else if (isCreateAccountButtonClicked(request)) {
            response.sendRedirect("createAccount");
        } else {
            reloadBecauseAuthenticateFailed(request, response);
        }
    }

    private boolean isCreateAccountButtonClicked(HttpServletRequest request) {
        return request.getParameter("createAccountButton") != null;
    }

    private void reloadBecauseAuthenticateFailed(HttpServletRequest request,
                                                 HttpServletResponse response)
        throws IOException, ServletException {
        forwardRequest(this, request, response, "/jsp/index.jsp");
    }
}
