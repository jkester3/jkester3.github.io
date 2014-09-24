package model;

import database.DataManager;
import database.Itinerary;
import database.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class LoginForm {
    private HttpServletRequest request;
    private String username;
    private String password;

    public boolean isAuthenticationSuccessful(HttpServletRequest request) {
        this.request = request;
        username = request.getParameter("username");
        password = request.getParameter("password");
        if (!isLoginButtonClicked()) {
            return false;
        }
        try {
            AccountValidation validation = new AccountValidation
                    (username, password);
            validation.setOperation(new LoginAccountOperation());
            validation.validateCredentials();
            storeLoginAttributes();
            return true;
        } catch (SQLException ex) {
            request.setAttribute("error", ex.getMessage());
            return false;
        }
    }

    private boolean isLoginButtonClicked() {
        return request.getParameter("loginButton") != null;
    }

    private void storeLoginAttributes() throws SQLException {
        User currentAccount = DataManager.fetchUser(username);
        final int userID = currentAccount.getID();
        final List<Itinerary> itineraries = DataManager.getItineraryByUserID(userID);
        currentAccount.setItineraries(itineraries);
        final String welcomeName = currentAccount.getWelcomeName();
        final String firstName = currentAccount.getFirstName();
        final String lastName = currentAccount.getLastName();
        HttpSession session = request.getSession();
        session.setAttribute("welcomeName", welcomeName);
        session.setAttribute("currentUser", currentAccount);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("username", username);
    }
}
