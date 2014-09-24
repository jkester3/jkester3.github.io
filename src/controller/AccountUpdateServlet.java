package controller;

import model.AccountUpdateForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.ServletUtilities.forwardRequest;

@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/updateAccount"})
public class AccountUpdateServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect("jsp/update_account.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        if (isSubmitButtonClicked(request)) {
            doUpdateRequest(request, response);
        } else if (isDeleteButtonClicked(request)) {
            doDeleteRequest(request, response);
        } else {
            response.sendRedirect("jsp/update_account.jsp");
        }
    }

    private boolean isSubmitButtonClicked(HttpServletRequest request) {
        return request.getParameter("submitButton") != null;
    }

    private void doUpdateRequest(HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException, ServletException {
        AccountUpdateForm accountForm = new AccountUpdateForm(request);
        if (accountForm.isAccountUpdateSuccessful()) {
            redirectToSameScreen(response);
        } else {
            reloadBecauseAccountCreateFailed(request, response);
        }
    }

    private void redirectToSameScreen(HttpServletResponse response)
            throws IOException {
        response.sendRedirect("jsp/update_account.jsp");
    }

    private void reloadBecauseAccountCreateFailed(HttpServletRequest request,
                                                  HttpServletResponse response)
            throws IOException, ServletException {
        forwardRequest(this, request, response, "/jsp/update_account.jsp");
    }

    private boolean isDeleteButtonClicked(HttpServletRequest request) {
        return request.getParameter("deleteButton") != null;
    }

    private void doDeleteRequest(HttpServletRequest request,
                                 HttpServletResponse response)
        throws IOException, ServletException {
        AccountUpdateForm accountForm = new AccountUpdateForm(request);
        if (accountForm.hasAccountBeenDeleted()) {
            goToHomePage(response);
        }
    }

    private void goToHomePage(HttpServletResponse response)
            throws IOException {
        response.sendRedirect("jsp/deleteLoginSession.jsp");
    }
}
