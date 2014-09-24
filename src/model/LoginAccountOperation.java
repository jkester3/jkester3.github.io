package model;

import database.DataManager;
import database.User;

import java.sql.SQLException;

public class LoginAccountOperation implements Validation {
    private User account;
    private String username, password;

    public void init(User account, String username, String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }

    public void validateCredentials() throws SQLException {
        if (!usernameExists()) {
            throw new SQLException("Username " + "\"" + username + "\""
                + " does not exist. Please try again.");
        } else if (!passwordMatches()) {
            throw new SQLException("Authentication failed. "
                + "Please try again.");
        }
    }

    private boolean passwordMatches() {
        final int realPassword = account.getPassword();
        return realPassword == password.hashCode();
    }

    private boolean usernameExists() throws SQLException {
        User user = DataManager.fetchUser(username);
        return user.getUsername() != null;
    }
}
