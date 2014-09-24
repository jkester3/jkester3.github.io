package model;

import database.User;

import java.sql.SQLException;

public class UpdateAccountOperation implements Validation {
    private int password;
    private String confirmPassword;

    public void init(User account, String username, String password) {
        this.password = account.getPassword();
        this.confirmPassword = password;
    }

    public void validateCredentials() throws SQLException {
        if (password != confirmPassword.hashCode()) {
            throw new SQLException("Passwords do not match. "
                    + "Please try again. " + password + " " + confirmPassword);
        }
    }
}
