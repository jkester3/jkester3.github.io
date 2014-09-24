
package controller;

import static model.ServletUtilities.*;

import model.AccountCreateForm;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountCreationServlet", urlPatterns = {"/createAccount"})
public class AccountCreationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException {
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException {
        if (isSubmitButtonClicked(request)) {
            doCreateRequest(request, response);
        }
    }

    private boolean isSubmitButtonClicked(HttpServletRequest request) {
        return request.getParameter("signUpButton") != null;
    }

    private void doCreateRequest(HttpServletRequest request,
                                 HttpServletResponse response)
        throws IOException, ServletException {
        AccountCreateForm accountForm = new AccountCreateForm(request);
        if (accountForm.isAccountCreationSuccessful()) {
            automaticallyLogin(request, response);
        } else {
            reloadBecauseAccountCreateFailed(request, response);
        }
    }

    private void automaticallyLogin(HttpServletRequest request,
                                    HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("accountCreateSuccess",
                "Account successfuly created. Please log in.");
        response.sendRedirect("jsp/index.jsp");
    }

    private void reloadBecauseAccountCreateFailed(HttpServletRequest request,
                                                  HttpServletResponse response)
            throws IOException, ServletException {
        forwardRequest(this, request, response, "/jsp/index.jsp");
    }
}
