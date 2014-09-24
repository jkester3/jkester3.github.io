package model;

import database.DataManager;
import database.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AccountCreateForm {
    private HttpServletRequest request;
    private HttpSession session;
    private String firstName, lastName, username;
    private String password, confirmPassword;
    private User newAccount;

    public AccountCreateForm(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
    }

    public boolean isAccountCreationSuccessful() {
        try {
            gatherNewAccountInfo();
            AccountValidation validation = new AccountValidation
                    (newAccount, confirmPassword);
            validation.setOperation(new CreateAccountOperation());
            validation.validateCredentials();
            saveAccountSettings();
            storeSessionAttributes();
            return true;
        } catch (SQLException ex) {
            storeAttributesForNextAttempt(request);
            request.setAttribute("error", ex.getMessage());
            return false;
        }
    }

    private void gatherNewAccountInfo() {
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        username = request.getParameter("newUsername");
        password = request.getParameter("newPassword");
        confirmPassword = request.getParameter("confirmPassword");
        newAccount = new User(firstName, lastName, username, password.hashCode());
    }

    private void saveAccountSettings() throws SQLException {
        DataManager.createUser(newAccount);
    }

    private void storeSessionAttributes() {
        synchronized(session) {
            String welcomeName = newAccount.getWelcomeName();
            session.setAttribute("currentUser", newAccount);
            session.setAttribute("username", username);
            session.setAttribute("welcomeName", welcomeName);
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);
        }
    }

    /* These attributes repopulate previously filled-in fields for the user
     * following a page reload due to an error being thrown
     */
    private void storeAttributesForNextAttempt(HttpServletRequest request) {
        request.setAttribute("prevFirstName", firstName);
        request.setAttribute("prevLastName", lastName);
        request.setAttribute("prevUsername", username);
    }
}
