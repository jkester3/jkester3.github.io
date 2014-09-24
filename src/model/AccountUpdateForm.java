package model;

import database.DataManager;
import database.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AccountUpdateForm {
    private HttpServletRequest request;
    private HttpSession session;
    private User currentAccount;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName, lastName;

    public AccountUpdateForm(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
        this.username = session.getAttribute("username").toString();
    }

    public boolean isAccountUpdateSuccessful() {
        try {
            gatherNewAccountInfo();
            AccountValidation validation = new AccountValidation
                    (currentAccount, confirmPassword);
            validation.setOperation(new UpdateAccountOperation());
            validation.validateCredentials();
            updateAccountSettings();
            updateSessionAttributes();
            return true;
        } catch (SQLException ex) {
            request.setAttribute("error", ex.getMessage());
            return false;
        }
    }

    private void gatherNewAccountInfo() {
        firstName = request.getParameter("updateFirstName");
        lastName = request.getParameter("updateLastName");
        password = request.getParameter("oldPassword");
        confirmPassword = request.getParameter("confirmOldPassword");
        currentAccount = new User(firstName, lastName, username,
                password.hashCode());
    }

    private void updateSessionAttributes() {
        String welcomeName = currentAccount.getWelcomeName();
        synchronized(session) {
            session.setAttribute("currentUser", currentAccount);
            session.setAttribute("welcomeName", welcomeName);
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);
        }
    }

    private void updateAccountSettings() throws SQLException {
        if (isPasswordEmpty()) {
            revertToOldPasswordAndUpdateAccount();
        }
        DataManager.updateUser(currentAccount);
    }

    private boolean isPasswordEmpty() {
        return password.isEmpty() && confirmPassword.isEmpty();
    }

    private void revertToOldPasswordAndUpdateAccount() throws SQLException {
        final User accountBeforeChange = DataManager.fetchUser(username);
        final int passwordBeforeChange = accountBeforeChange.getPassword();
        currentAccount.setPassword(passwordBeforeChange);
    }

    public boolean hasAccountBeenDeleted() {
        final String username = session.getAttribute("username").toString();
        try {
            deleteAccount(username);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void deleteAccount(String username) throws SQLException {
        DataManager.deleteUser(username);
    }
}
